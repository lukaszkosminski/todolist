let addList = document.querySelector(".addList");
addList.addEventListener("click", () => {
    if(addList.querySelector(".text") != null) {
        addList.removeChild(addList.querySelector(".text"));
        let span = document.createElement("span");
        span.classList.add("listForm");
            let form = document.createElement("form");
            form.classList.add("form");
            form.autocomplete = "off";
                let listName = document.createElement("div");
                listName.classList.add("listName");
                    let listNameInput = document.createElement("input");
                    listNameInput.type = "text";
                    listNameInput.required = true;
                    listNameInput.name = "name";
                    listNameInput.classList.add("name");
                    listNameInput.classList.add("input");
                listName.appendChild(listNameInput);
                    let listNameLabel = document.createElement("label");
                    listNameLabel.innerText = "Collection list name";
                    listNameLabel.classList.add("label");
                listName.appendChild(listNameLabel);
            form.appendChild(listName);
                let csrfInput = document.createElement("input");
                csrfInput.type= "hidden";
                csrfInput.name = "_csrf.parameterName";
                csrfInput.value= "_csrf.token";
            form.appendChild(csrfInput);
                let submitButton = document.createElement("input");
                submitButton.type = "submit";
                submitButton.value = "add Collection List";
            form.appendChild(submitButton);
        span.appendChild(form);
        addList.appendChild(span);

        document.querySelector('.form').addEventListener("submit", function (event) {
            event.preventDefault();
            let formData = {
                name: document.querySelector(".name").value
            }
            fetch("http://localhost:8095/api/user/create-task-collection", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData),
            }).then(response => response.json()).then(data => {
                window.location.href = `/list/${data.id}`;
            })
        });

        document.addEventListener("click", function (event) {
            if (addList && !addList.contains(event.target) && addList.contains(document.querySelector(".listForm"))) {
                addList.removeChild(document.querySelector(".listForm"));
                let text = document.createElement("span")
                text.classList.add("text");
                text.innerText = "Add Collection Task";
                addList.appendChild(text);
            }
        });
    }
})