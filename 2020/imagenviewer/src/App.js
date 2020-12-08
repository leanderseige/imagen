import logo from './imagen.png';
import './App.css';
import Viewer from './components/Viewer.js';


function App() {
  return (
    <div className="App">
      <img src={logo} alt="logo" />
      <p>ImageN is an image generator that uses a single integer with an accuracy of 65535 bits to generate every possible image. The generator initialized the integer with the value 0 and now constantly adds the value 1 in order to generate every possible pattern and thus every possible image.</p>
      <p>ImageN is an art project that works with the contrast of computability and complexity.</p>
      <Viewer />
      <p>The generator is a tiny C programm calculating millions of patterns per second. The original interface was a Java applet with many options to turn the pattern into images. Currently I can only provide a simple Javascript replacement. I hope to improve it in the future. The raw data is accessible at <a href="https://imagen.manducus.net/seed">https://imagen.manducus.net/seed</a>.</p>
      <p>Further information and source codes can be found at<br /><a href="https://github.com/leanderseige/imagen" target="_blank">https://github.com/leanderseige/imagen</a></p>
      <p>&copy; 2020, Leander Seige</p>
    </div>
  );
}

export default App;
