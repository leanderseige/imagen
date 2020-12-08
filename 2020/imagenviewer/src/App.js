import logo from './imagen.png';
import './App.css';
import Viewer from './components/Viewer.js';


function App() {
  return (
    <div className="App">
      <img src={logo} alt="logo" />
      <Viewer />
    </div>
  );
}

export default App;
