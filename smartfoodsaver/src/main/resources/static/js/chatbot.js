(function () {
  // Helper to find the chat box when the fragment is present
  const getBox = () => document.getElementById("sfs-chatbox");
  const getInput = () => document.getElementById("sfs-chat-input");
  const getMsgs = () => document.getElementById("sfs-chat-messages");

  const persist = (isOpen) => {
    try { localStorage.setItem("sfsChatOpen", isOpen ? "1" : "0"); } catch (_) {}
  };

  const show = () => {
    const box = getBox();
    if (!box) return;
    box.classList.remove("hidden");
    persist(true);
    const input = getInput();
    if (input) input.focus();
  };

  const hide = () => {
    const box = getBox();
    if (!box) return;
    box.classList.add("hidden");
    persist(false);
  };

  const toggle = () => {
    const box = getBox();
    if (!box) return;
    box.classList.toggle("hidden");
    persist(!box.classList.contains("hidden"));
    if (!box.classList.contains("hidden")) {
      const input = getInput();
      if (input) input.focus();
    }
  };

  // Event delegation so it works even if the link/fragment loads later
  document.addEventListener("click", (e) => {
    const tgt = e.target.closest("#chat-toggle, #sfs-chat-launcher, #sfs-chat-close");
    if (!tgt) return;

    // Prevent default for <a href="#"> and buttons inside forms
    if (tgt.matches("#chat-toggle, #sfs-chat-launcher, #sfs-chat-close")) {
      if (typeof e.preventDefault === "function") e.preventDefault();
    }

    if (tgt.id === "chat-toggle" || tgt.id === "sfs-chat-launcher") {
      toggle();
    } else if (tgt.id === "sfs-chat-close") {
      hide();
    }
  });

  // ESC closes
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") hide();
  });

  // Restore prior state (if the fragment is present)
  document.addEventListener("DOMContentLoaded", () => {
    const wantedOpen = (localStorage.getItem("sfsChatOpen") === "1");
    const box = getBox();
    if (!box) return; // fragment not on this page
    if (wantedOpen) show(); else hide();
  });

  // Handle form submit if present
  document.addEventListener("submit", async (e) => {
    const form = e.target.closest("#sfs-chat-form");
    if (!form) return;
    e.preventDefault();

    const input = getInput();
    const msgs = getMsgs();
    const text = (input?.value || "").trim();
    if (!text || !msgs) return;

    const addMsg = (txt, who) => {
      const div = document.createElement("div");
      div.className = `sfs-msg ${who}`;
      div.textContent = txt;
      msgs.appendChild(div);
      msgs.scrollTop = msgs.scrollHeight;
    };

    addMsg(text, "user");
    if (input) input.value = "";

    try {
      const headers = { "Content-Type": "application/json" };
      const csrfTokenEl  = document.querySelector('meta[name="_csrf"]');
      const csrfHeaderEl = document.querySelector('meta[name="_csrf_header"]');
      if (csrfTokenEl && csrfHeaderEl) {
        headers[csrfHeaderEl.content] = csrfTokenEl.content;
      }

      const res = await fetch("/api/chat", {
        method: "POST",
        headers,
        body: JSON.stringify({ message: text })
      });

      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const data = await res.json().catch(() => ({}));

      addMsg(
        data && typeof data.reply === "string" && data.reply.length
          ? data.reply
          : "I couldn’t find that—try milk, bread, rice, or leftovers.",
        "bot"
      );
    } catch (err) {
      addMsg("Network error — please try again.", "bot");
    }
  });

  try {
    const qs = new URLSearchParams(location.search);
    if (qs.get("chat") === "1") {
      // Wait a tick for fragment to mount, then open
      window.requestAnimationFrame(() => show());
    }
  } catch (_) {}
})();
