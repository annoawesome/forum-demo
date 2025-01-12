const threadTitle = document.getElementById('thread-title');
const repliesList = document.getElementById('replies-list');

function getThreadId() {
    return new URLSearchParams(window.location.search).get('id')
}

function addPostElement(author, userRole, content) {
    const postElement = document.createElement("div")
    postElement.classList.add("post");

    const usernameElement = document.createElement("p")
    usernameElement.innerText = author;

    if (userRole !== "") {
        usernameElement.innerHTML += `<i class='user-role'>${userRole}</i>`;
    }

    const contentElement = document.createElement("p")
    contentElement.innerText = content;

    postElement.append(usernameElement);
    postElement.append(contentElement);

    repliesList.appendChild(postElement);
}

function clearThreadElements() {
    repliesList.innerHTML = "";
}

function updateThreadElements(thread) {
    threadTitle.innerText = thread.title;
    addPostElement(thread.author, "OP", thread.content);

    for (reply of thread.children) {
        const userRole = reply.author === thread.author ? "OP" : "";

        addPostElement(reply.author, userRole, reply.content)
    }
}

function getThread(id) {
    fetch(`/threads/${id}`)
    .then(response => response.json())
    .then(threadData => {
        clearThreadElements(threadData);
        updateThreadElements(threadData);

        document.getElementsByTagName("title")[0].innerText = threadData.title + " | Forum Demo";
    })
}

function replyToThread(threadId, replyContent) {
    fetch(`/threads/${threadId}/reply`, {
        method: 'POST',
        body: JSON.stringify({
            content: replyContent,
        }),
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
        }
    })
    .then(_ => {
        getThread(getThreadId())
    })
}

function submitReply() {
    let replyContent = document.getElementById('reply-content').value;

    replyToThread(getThreadId(), replyContent);
}

getThread(getThreadId());