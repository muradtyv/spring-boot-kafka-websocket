import { useState } from 'react'
import './App.css'
import UsernamePage from './components/UserNamePage';

function App() {
  const [username, setUsername] = useState(null);

  return (
    <div>
      {username && <UsernamePage setUsername={setUsername} />}
    </div>
  );
}

export default App
