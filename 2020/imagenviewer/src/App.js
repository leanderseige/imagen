import logo from './imagen.png';
import './App.css';
import Viewer from './components/Viewer.js';


function App() {
  return (
    <div className="App">
      <img src={logo} alt="logo" />
      <p>ImageN is an image generator that holds a single integer with an accuracy of 65535 bits on the server. The generator started with this number, which was originally set to 0, and now constantly adds the value 1. This process aims to generate all possible numbers, bit patterns and images.</p>
      <p>ImageN is an art project that works with the opposites of calculability and complexity.</p>
      <Viewer />
      <p>contemporary and historic source codes can be found on <a href="https://github.com/leanderseige/imagen" target="_blank">https://github.com/leanderseige/imagen</a></p>
    </div>
  );
}

export default App;
