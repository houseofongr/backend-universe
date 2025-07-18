window.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll("pre code.language-json").forEach((codeBlock) => {
        const pre = codeBlock.parentElement;

        // 버튼 생성
        const copyBtn = document.createElement("button");
        copyBtn.textContent = "📋 JSON 복사";
        copyBtn.style.display = "block";
        copyBtn.style.marginBottom = "6px";
        copyBtn.style.padding = "4px 8px";
        copyBtn.style.fontSize = "12px";
        copyBtn.style.cursor = "pointer";

        // 버튼 동작
        copyBtn.onclick = () => {
            const text = codeBlock.textContent.trim();
            navigator.clipboard.writeText(text).then(() => {
                copyBtn.textContent = "✅ 복사됨!";
                setTimeout(() => {
                    copyBtn.textContent = "📋 JSON 복사";
                }, 2000);
            });
        };

        // 버튼 삽입
        pre.parentElement.insertBefore(copyBtn, pre);
    });
});
