import { useState } from 'react';
import { useTodoStore } from '../store/todoStore';
import { shallow } from 'zustand/shallow';
import './components.css';

function TodoItem({ todo }) {
  const { updateTodo, deleteTodo } = useTodoStore((state) => ({
    updateTodo: state.updateTodo,
    deleteTodo: state.deleteTodo,
  }), shallow);
  const [isEditing, setIsEditing] = useState(false);
  const [editText, setEditText] = useState(todo.title);

  const handleToggleComplete = () => {
    updateTodo(todo.id, { completed: !todo.completed });
  };

  const handleEditStart = () => {
    setIsEditing(true);
  };

  const handleEditCancel = () => {
    setIsEditing(false);
    setEditText(todo.title); // 원래 제목으로 되돌림
  };

  const handleEditSave = () => {
    if (!editText.trim()) {
      alert("할 일 제목이 비어있습니다.");
      return;
    }
    updateTodo(todo.id, { title: editText });
    setIsEditing(false);
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      handleEditSave();
    } else if (e.key === 'Escape') {
      handleEditCancel();
    }
  };

  return (
    <li className={`todo-item ${todo.completed ? 'completed' : ''} ${isEditing ? 'editing' : ''}`}>
      {isEditing ? (
        <input
          type="text"
          value={editText}
          onChange={(e) => setEditText(e.target.value)}
          onBlur={handleEditSave}
          onKeyDown={handleKeyDown}
          autoFocus
          className="todo-edit-input"
        />
      ) : (
        <>
          <div className="todo-item-main" onClick={handleToggleComplete}>
            <span className="todo-title">{todo.title}</span>
            {todo.content && <p className="todo-content-display">{todo.content}</p>}
          </div>
          <div className="todo-actions">
            <button onClick={(e) => { e.stopPropagation(); handleEditStart(); }} className="edit-btn">
              수정
            </button>
            <button onClick={(e) => { e.stopPropagation(); deleteTodo(todo.id); }} className="delete-btn">
              삭제
            </button>
          </div>
        </>
      )}
    </li>
  );
}

export default TodoItem;