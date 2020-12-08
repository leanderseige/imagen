import React from 'react';
import DisplayCanvas from './DisplayCanvas.js';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormLabel from '@material-ui/core/FormLabel';

class Viewer extends React.Component {
    constructor(props) {
        super(props);
        this.updateData = this.updateData.bind(this);
        this.setColorMode = this.setColorMode.bind(this);
        this.state = {
          rawdata: false,
          onestring: false,
          numbers: false,
          colormode: 1
        }
    }

  setColorMode(event) {
      this.setState({colormode:event.target.value});
  }

  updateData() {
    fetch("https://imagen.manducus.net/seed")
      .then(response => response.text())
      .then(rawdata => {
        let onestring = rawdata.replaceAll('\n',',')
        onestring = onestring.replaceAll(' ','')
        onestring = onestring.replaceAll(/([,0]+$)/g,'')
        let numbers = onestring.split(',')
        this.setState({
          rawdata:rawdata,
          onestring: onestring,
          numbers: numbers
        })
        setTimeout(this.updateData,100)
      });
  }

  componentDidMount() {
    this.updateData()
  }

  render() {
    return(
      <div>
        <DisplayCanvas numbers={this.state.numbers} colormode={this.state.colormode} />
        <FormControl component="fieldset">
          <FormLabel component="legend">Color Mode</FormLabel>
          <RadioGroup aria-label="gender" name="gender1" value={this.state.colormode} onChange={this.setColorMode}>
            <FormControlLabel value="1" control={<Radio />} label="1 Bit, B/W" />
            <FormControlLabel value="2" control={<Radio />} label="2 Bit, Grey" />
            <FormControlLabel value="4" control={<Radio />} label="4 Bit, Color" />
          </RadioGroup>
        </FormControl>
      </div>
    )
  }
}

export default Viewer
