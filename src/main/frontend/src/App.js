import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
    const [message, setMessage] = useState([]);
    const [todos, setTodos] = useState([]);
    const [newTodo, setNewTodo] = useState("");
    const [deadline, setDeadline] = useState("");
    const [editingId, setEditingId] = useState(null);

    // Fetch welcome message
    useEffect(() => {
        fetch("/api/demo-web")
            .then((response) => response.json())
            .then((data) => setMessage(data))
            .catch((error) => console.error("Error fetching message:", error));
    }, []);

    // Fetch all todos
    useEffect(() => {
        fetch("/todo/get-all")
            .then((response) => response.json())
            .then((data) => setTodos(data))
            .catch((error) => console.error("Error fetching todos:", error));
    }, []);

    // Save or update a todo
    const saveTodo = (todo) => {
        const isNew = !todo.id;
        const url = isNew ? "/todo/save" : `/todo/update/${todo.id}`;
        const method = isNew ? "POST" : "POST";

        fetch(url, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(todo),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Error: ${response.status} ${response.statusText}`);
                }
                return response.json();
            })
            .then((savedTodo) => {
                setTodos((prevTodos) =>
                    isNew
                        ? [...prevTodos, savedTodo]
                        : prevTodos.map((t) => (t.id === savedTodo.id ? savedTodo : t))
                );
                resetForm();
            })
            .catch((error) => console.error(`Error ${isNew ? "adding" : "updating"} todo:`, error));
    };

    // Reset form fields
    const resetForm = () => {
        setNewTodo("");
        setDeadline("");
        setEditingId(null);
    };

    // Add or update todo
    const addOrUpdateTodo = () => {
        if (!newTodo || !deadline) {
            alert("Both task and deadline are required.");
            return;
        }

        saveTodo({
            id: editingId,
            title: newTodo.trim(),
            description: "", // Default description
            completed: false, // Default to incomplete
            deadline,
        });
    };

    // Start editing a todo
    const startEditing = (todo) => {
        setNewTodo(todo.title);
        setDeadline(todo.deadline);
        setEditingId(todo.id);
    };

    // Delete a todo
    const deleteTodo = (id) => {
        fetch(`/todo/delete/${id}`, {
            method: "DELETE",
        })
            .then(() => setTodos((prevTodos) => prevTodos.filter((todo) => todo.id !== id)))
            .catch((error) => console.error("Error deleting todo:", error));
    };

    return (
        <div className="app-container">
            {/* Welcome Message */}
            <section className="welcome-section">
                <h1 className="welcome-title">TodoApp</h1>
                <h4 className="welcome-subtitle">Manage your schedule effortlessly with TodoApp!</h4>
                <div className="welcome-messages">
                    {message.map((msg, index) => (
                        <p key={index} className="welcome-message">{msg}</p>
                    ))}
                </div>
            </section>

            {/* Add/Update Todo */}
            <section className="add-todo-section">
                <input
                    type="text"
                    className="add-todo-input"
                    value={newTodo}
                    onChange={(e) => setNewTodo(e.target.value)}
                    placeholder="Enter a task"
                />
                <input
                    type="date"
                    className="add-todo-deadline"
                    value={deadline}
                    onChange={(e) => setDeadline(e.target.value)}
                />
                <button className="add-todo-button" onClick={addOrUpdateTodo}>
                    {editingId ? "Update Todo" : "Add Todo"}
                </button>
            </section>

            {/* Todo List */}
            <section className="todo-section">
                <ul className="todo-list">
                    {todos.map((todo) => (
                        <li key={todo.id} className="todo-item">
                            <div className="todo-details" style={{ textAlign: "left" }}>
                                <span className="todo-id"><strong>ID:</strong> {todo.id}</span>
                                <span className="todo-content">{todo.title.trim()}</span>
                                <span className="todo-deadline">
                                    <strong>Deadline:</strong> {todo.deadline || "No deadline"}
                                </span>
                                <span className="todo-status">
                                    <strong>Status:</strong> {todo.completed ? "Completed" : "Incomplete"}
                                </span>
                            </div>
                            <button
                                className="todo-edit-button"
                                onClick={() => startEditing(todo)}
                            >
                                ✎ Edit
                            </button>
                            <button
                                className="todo-delete-button"
                                onClick={() => deleteTodo(todo.id)}
                            >
                                ✖ Delete
                            </button>
                        </li>
                    ))}
                </ul>
            </section>
        </div>
    );
}

export default App;

