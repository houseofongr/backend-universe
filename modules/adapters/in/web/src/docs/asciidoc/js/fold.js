document.addEventListener("DOMContentLoaded", function () {
    function makeCollapsible(container, type = "section") {
        container.style.maxHeight = "200px";
        container.style.overflow = "hidden";
        container.style.border = "1px solid #ddd";
        container.style.borderRadius = "8px";
        container.style.position = "relative";
        container.style.marginBottom = "1em";
        container.style.paddingTop = "2.5em";
        container.style.backgroundColor = "#f9f9f9";

        const toggleBtn = document.createElement("button");
        toggleBtn.innerHTML = "🔽";
        toggleBtn.className = `${type}-toggle-btn`;

        let folded = true;

        toggleBtn.addEventListener("click", function () {
            folded = !folded;
            container.style.maxHeight = folded ? "200px" : "1000px";
            container.style.overflow = folded ? "hidden" : "auto";
            toggleBtn.innerHTML = folded ? "🔽" : "🔼";
        });

        container.appendChild(toggleBtn);
    }

    // 코드 블록 접기
    document.querySelectorAll("pre code").forEach(function (block) {
        const parent = block.parentElement;
        makeCollapsible(parent, "code");
    });

    // 테이블 접기 (div로 wrapping)
    document.querySelectorAll("table").forEach(function (table) {
        const wrapper = document.createElement("div");
        table.parentNode.insertBefore(wrapper, table);
        wrapper.appendChild(table);
        makeCollapsible(wrapper, "table");
    });
});
