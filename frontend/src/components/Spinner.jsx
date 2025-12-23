import '../App.css'; // 기존 App.css의 스피너 스타일을 재사용합니다.

function Spinner() {
  return (
    <div className="spinner-overlay"><div className="spinner"></div></div>
  );
}

export default Spinner;