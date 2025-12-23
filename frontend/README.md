# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) (or [oxc](https://oxc.rs) when used in [rolldown-vite](https://vite.dev/guide/rolldown)) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.


/frontend/src
├── api                 # API 통신 함수
│   └── postsApi.js
├── assets              # 이미지 등 정적 파일
├── components          # 재사용 가능한 작은 UI 요소
│   ├── Button.jsx
│   └── Header.jsx
├── pages               # 화면 단위의 컴포넌트
│   ├── BoardPage.jsx
│   └── PostDetailPage.jsx
├── styles              # 전역 스타일
│   └── variables.css
├── App.jsx             # pages 컴포넌트를 조합하여 라우팅 처리
└── main.jsx            # 애플리케이션 진입점
