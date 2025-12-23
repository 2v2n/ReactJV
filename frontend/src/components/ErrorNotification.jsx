import '../App.css'; // 기존 App.css의 에러 알림 스타일을 재사용합니다.

function ErrorNotification({ message }) {
  return (
    <div className="error-notification">{message}</div>
  );
}

export default ErrorNotification;