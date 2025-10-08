(function () {
  const box = document.getElementById("sfs-chatbox");
  const openBtn = document.getElementById("sfs-chat-launcher"); // bubble (may be hidden)
  const navToggle = document.getElementById("chat-toggle");      // <a> in navbar
  const closeBtn = document.getElementById("sfs-chat-close");
  const msgs = document.getElementById("sfs-chat-messages");
  const form = document.getElementById("sfs-chat-form");
  const input = document.getElementById("sfs-chat-input");

  const toggle = () => {
    box.classList.toggle("hidden");
    if (!box.classList.contains("hidden")) input.focus();
  };

  const addMsg = (text, who) => {
    const div = document.createElement("div");
    div.className = `sfs-msg ${who}`;
    div.textContent = text;
    msgs.appendChild(div);
    msgs.scrollTop = msgs.scrollHeight;
  };

  // Bubble (if present)
  if (openBtn) openBtn.addEventListener("click", toggle);

  // Navbar link/button
  if (navToggle) {
    navToggle.addEventListener("click", (e) => {
      e.preventDefault(); // stop page jump
      toggle();
    });
  }

  closeBtn.addEventListener("click", () => box.classList.add("hidden"));

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const text = (input.value || "").trim();
    if (!text) return;
    addMsg(text, "user");
    input.value = "";

    try {
      const res = await fetch("/api/chat", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: text })
      });
      const data = await res.json();
      addMsg(data.reply ?? "I couldn’t find that—try milk, bread, rice, or leftovers.", "bot");
    } catch {
      addMsg("Network error — please try again.", "bot");
    }
  });
})();
