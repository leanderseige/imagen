import logo from './imagen.png';
import './App.css';
import Viewer from './components/Viewer.js';


function App() {
  return (
    <div className="App">
      <img src={logo} alt="logo" />
      <p>ImageN is an image generator that uses a single integer with an accuracy of 65535 bits to generate every possible image. The generator initialised the integer with the value 0 and now constantly adds the value 1 in order to generate every possible pattern and so every possible image.</p>
      <p>ImageN is an art project that works with the opposites of calculability and complexity.</p>
      <Viewer />
      <p>The generator is a tiny C programm calculating millions of patterns per second. The original interface was a Java applet with many options to turn the pattern into images. Currently I can only provide a simple Javascript replacement. I hope to improve it in the future. The raw data can be accessed at <a href="http://imagen.manducus.net/seed">http://imagen.manducus.net/seed</a>.</p>
      <p>contemporary and historic source codes can be found on <a href="https://github.com/leanderseige/imagen" target="_blank">https://github.com/leanderseige/imagen</a></p>
      <p>The generator started in the year 2000 but was offline from 2004 to 2020.</p>
    </div>
  );
}

export default App;
