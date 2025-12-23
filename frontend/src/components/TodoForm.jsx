import { useState } from 'react';
import { useTodoStore } from '../store/todoStore';
import './components.css';

function TodoForm() {
  const addTodo = useTodoStore(state => state.addTodo);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [isAdding, setIsAdding] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!title.trim() || isAdding) {
      alert('할 일 제목을 입력해주세요.');
      return;
    }
    setIsAdding(true);
    await addTodo(title, content);
    setTitle('');
    setContent('');
    setIsAdding(false);
  };

  return (
    <form onSubmit={handleSubmit} className="todo-form">
      <input
        type="text"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        placeholder="새로운 할 일을 입력하세요"
        className="todo-input"
      />
      <textarea
        value={content}
        onChange={(e) => setContent(e.target.value)}
        placeholder="내용 (선택 사항)"
        rows="3"
        className="todo-textarea"
      ></textarea>
      <button type="submit" className="todo-add-button" disabled={isAdding}>
        {isAdding ? '추가 중...' : 'Add'}
      </button>
    </form>
  );
}

export default TodoForm;