import React from 'react';
import DisplayCanvas from './DisplayCanvas.js';

class Viewer extends React.Component {
    constructor(props) {
        super(props);
        this.updateData = this.updateData.bind(this);
        this.state = {
          rawdata: false,
          onestring: false,
          numbers: false
        }
    }

  async updateData() {
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
        <DisplayCanvas numbers={this.state.numbers} />
      </div>
    )
  }
}

export default Viewer
