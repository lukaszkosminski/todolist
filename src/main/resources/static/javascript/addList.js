let addList = document.querySelector(".addList");
addList.addEventListener("click", () => {
    if(addList.querySelector(".text") != null) {
        addList.removeChild(addList.querySelector(".text"));
        let span = document.createElement("span");
        span.classList.add("listForm");
            let form = document.createElement("form");
            form.classList.add("form");
            form.setAttribute("autocomplete", "off");
                let listName = document.createElement("div");
                listName.classList.add("listName");
                    let listNameInput = document.createElement("input");
                    listNameInput.setAttribute("type", "text");
                    listNameInput.setAttribute("required", "");
                    listNameInput.setAttribute("name", "name");
                    listNameInput.classList.add("name");
                    listNameInput.classList.add("input");
                listName.appendChild(listNameInput);
                    let listNameLabel = document.createElement("label");
                    listNameLabel.innerText = "Listname";
                    listNameLabel.classList.add("label");
                listName.appendChild(listNameLabel);
            form.appendChild(listName);
                let submitButton = document.createElement("input");
                submitButton.setAttribute("type", "submit");
                submitButton.setAttribute("value", "Add Task");
            form.appendChild(submitButton);
        span.appendChild(form);
        addList.appendChild(span);

        document.querySelector('.form').addEventListener("submit", function (event) {
            event.preventDefault();
            let formData = {
                name: document.querySelector(".name").value
            };
            fetch("api/user/create-tasklist", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            }).then(response => response.json()).then(data => {
                window.location.href = `/list/${formData.name}`;
            })
        });

        document.addEventListener("click", function (event) {
            if (addList && !addList.contains(event.target) && addList.contains(document.querySelector(".listForm"))) {
                addList.removeChild(document.querySelector(".listForm"));
                let text = document.createElement("span")
                text.classList.add("text");
                text.innerText = "AddList";
                addList.appendChild(text);
            }
        });
    }
})