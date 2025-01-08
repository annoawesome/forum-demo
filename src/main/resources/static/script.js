const THREAD_TABLE = document.getElementById("thread-table");

function loadThreads() {
    fetch('/threads')
        .then(response => response.json())
        .then(data => {
            console.log(data);

            for (let i = 0; i < data.length; i++) {
                const threadRow = document.createElement("tr");
                const td1 = document.createElement("td");
                const td2 = document.createElement("td");

                td1.innerText = data[i].title;
                td2.innerText = data[i].author;
                threadRow.appendChild(td1);
                threadRow.appendChild(td2);
                THREAD_TABLE.appendChild(threadRow);
            }
        })
}

function clearThreads() {
    THREAD_TABLE.innerHTML = '';

    const threadRow = document.createElement("tr");
    const td1 = document.createElement("td");
    const td2 = document.createElement("td");

    td1.innerText = "Thread"
    td2.innerText = "Author"
    threadRow.appendChild(td1);
    threadRow.appendChild(td2);
    THREAD_TABLE.appendChild(threadRow);
}

function openNewThreadForm() {
    const threadForm = document.getElementById("new-thread-form");
    threadForm.style.display = "flex";
}

function createForumThread() {
    const titleInput = document.getElementById("title-input");
    const contentInput = document.getElementById("content-input");

    fetch('/threads', {
        method: "POST",
        body: JSON.stringify({
            title: titleInput.value,
            content: contentInput.value
        }),
        headers: {
            "Content-Type": "application/json",
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
        }
    })
        .then(response => response.json())
        .then(_ => {
            clearThreads();
            loadThreads();
        })
}

loadThreads();