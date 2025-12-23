import { create } from 'zustand';
import {
  fetchTodos as apiFetchTodos,
  addTodo as apiAddTodo,
  updateTodo as apiUpdateTodo,
  deleteTodo as apiDeleteTodo
} from '../api';

export const useTodoStore = create((set, get) => ({
  // State
  todos: [],
  loading: false,
  error: null,

  fetchTodos: async () => {
    set({ loading: true });
    try {
      const data = await apiFetchTodos();
      set({ todos: data, loading: false });
    } catch (error) {
      console.error("Error fetching todos:", error);
      set({ error: '할 일 목록을 불러오는 데 실패했습니다.', loading: false });
      setTimeout(() => set({ error: null }), 5000);
    }
  },

  addTodo: async (title, content) => {
    // addTodo는 로딩 상태를 변경하지 않으므로, 낙관적 업데이트를 위해 그대로 둘 수 있습니다.
    try {
      const newTodo = await apiAddTodo({ title, content });
      set(state => ({ todos: [newTodo, ...state.todos] }));
    } catch (error) {
      console.error("Error adding todo:", error);
      set({ error: '할 일을 추가하는 데 실패했습니다.' });
      setTimeout(() => set({ error: null }), 5000);
    }
  },

  updateTodo: async (id, updatedData) => {
    set({ loading: true });
    try {
      const updatedTodo = await apiUpdateTodo(id, updatedData);
      set(state => ({
        todos: state.todos.map(todo => (todo.id === id ? updatedTodo : todo)),
        loading: false
      }));
    } catch (error) {
      console.error("Error updating todo:", error);
      set({ error: '할 일을 업데이트하는 데 실패했습니다.', loading: false });
      setTimeout(() => set({ error: null }), 5000);
    }
  },

  deleteTodo: async (id) => {
    const originalTodos = get().todos;
    set(state => ({ todos: state.todos.filter(todo => todo.id !== id) }));
    try {
      await apiDeleteTodo(id);
    } catch (error) {
      console.error("Error deleting todo:", error);
      set({ todos: originalTodos, error: '할 일을 삭제하는 데 실패했습니다.' }); // Revert on error
      setTimeout(() => set({ error: null }), 5000);
    }
  },
}));