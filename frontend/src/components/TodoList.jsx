import TodoItem from './TodoItem';
import { useTodoStore } from '../store/todoStore';
import { shallow } from 'zustand/shallow';
import './components.css';

function TodoList() {
  // useTodoStore를 한 번만 호출하여 필요한 상태를 모두 가져옵니다.
  const { todos, isLoading } = useTodoStore((state) => ({
    todos: state.todos,
    isLoading: state.loading,
  }), shallow);

  if (todos.length === 0 && !isLoading) {
    return <p className="empty-message">할 일이 없습니다. 새 할 일을 추가해보세요!</p>;
  }

  return (
    <ul className="todo-list">
      {todos.map(todo => (
        <TodoItem key={todo.id} todo={todo} />
      ))}
    </ul>
  );
}

export default TodoList;