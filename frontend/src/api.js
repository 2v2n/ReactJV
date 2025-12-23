import axios from 'axios';

const API_URL = 'http://localhost:8081/api/todos';

// GET: 모든 To-Do 목록 가져오기
export const fetchTodos = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

// POST: 새로운 To-Do 추가하기
export const addTodo = async ({ title, content }) => {
  const response = await axios.post(API_URL, {
    title,
    content,
    completed: false
  });
  return response.data;
};

// PUT: To-Do 업데이트 (완료 여부, 내용 등)
export const updateTodo = async (id, updatedData) => {
  const response = await axios.patch(`${API_URL}/${id}`, updatedData);
  return response.data;
};

// DELETE: To-Do 삭제하기
export const deleteTodo = async (id) => {
  await axios.delete(`${API_URL}/${id}`);
};