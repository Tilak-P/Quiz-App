const API_URL = 'http://localhost:8080';

document.getElementById('add-question-form').addEventListener('submit', addQuestion);
document.getElementById('create-quiz-form').addEventListener('submit', createQuiz);

function fetchAllQuestions() {
    fetch(`${API_URL}/question/allQuestions`)
        .then(response => response.json())
        .then(data => {
            const questionsList = document.getElementById('questions-list');
            questionsList.innerHTML = '';
            data.forEach(question => {
                const li = document.createElement('li');
                li.textContent = question.questionTitle;
                questionsList.appendChild(li);
            });
        })
        .catch(error => console.error('Error:', error));
}

function addQuestion(event) {
    event.preventDefault();

    const question = {
        questionTitle: document.getElementById('questionTitle').value,
        option1: document.getElementById('option1').value,
        option2: document.getElementById('option2').value,
        option3: document.getElementById('option3').value,
        option4: document.getElementById('option4').value,
        rightAnswer: document.getElementById('rightAnswer').value,
        difficultylevel: document.getElementById('difficultylevel').value,
        category: document.getElementById('category').value
    };

    fetch(`${API_URL}/question/addQuestion`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(question),
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            document.getElementById('add-question-form').reset();
        })
        .catch(error => console.error('Error:', error));
}

function createQuiz(event) {
    event.preventDefault();

    const category = document.getElementById('quizCategory').value;
    const numQ = document.getElementById('numQuestions').value;
    const title = document.getElementById('quizTitle').value;

    fetch(`${API_URL}/quiz/create?category=${category}&numQ=${numQ}&title=${title}`, {
        method: 'POST'
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            document.getElementById('create-quiz-form').reset();
        })
        .catch(error => console.error('Error:', error));
}