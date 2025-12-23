import { useEffect } from 'react';
import { useTodoStore } from './store/todoStore';
import { shallow } from 'zustand/shallow';
import TodoList from './components/TodoList';
import TodoForm from './components/TodoForm';
import Spinner from './components/Spinner';
import ErrorNotification from './components/ErrorNotification';
import './App.css';

function App() {
  // Zustand store에서 상태와 액션을 가져옵니다.
  // shallow 비교를 사용하여 불필요한 리렌더링을 방지합니다.
  const { loading, error, fetchTodos } = useTodoStore((state) => ({
    loading: state.loading,
    error: state.error,
    fetchTodos: state.fetchTodos,
  }), shallow);

  // 컴포넌트가 처음 렌더링될 때 To-Do 목록을 가져옵니다.
  useEffect(() => {
    fetchTodos();
    // useEffect의 dependency array에 fetchTodos를 추가하여 ESLint 경고를 방지합니다.
    // Zustand의 액션은 참조가 안정적이므로 리렌더링을 유발하지 않습니다.
  }, [fetchTodos]);

  return (
    <div className="App">
      {loading && <Spinner />}
      {error && <ErrorNotification message={error} />}

      <h1>To-Do List</h1>

      <TodoForm />

      <TodoList />
    </div>
  )
}

export default App
