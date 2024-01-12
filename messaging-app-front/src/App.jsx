import { useState } from 'react'
import './App.css'
import UsernamePage from './components/UserNamePage';
import ChatPage from "./components/ChatPage.jsx";

function App() {
  const [username, setUsername] = useState(null);

  return (
    <div>
      {username ? <ChatPage username={username}/> : <UsernamePage setUsername={setUsername} />}
    </div>
  );
}

export default App
