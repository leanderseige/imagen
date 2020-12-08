import React from 'react';

class DisplayCanvas extends React.Component {
    constructor(props) {
        super(props);
    }

  render() {
    let pixels = []
    let numberindex = 0
    let cindex={
      1: ["#000","#fff"],
      2: ["#000","#555","#aaa","#fff"],
      4: ["#000","#008","#00f","#088","#0ff","#080","#0f0","#800","#880","#808","#f00","#f0f","#ff0","#888","#ccc","#fff"]
    }
    let depth=this.props.colormode

    for(let y=0; y<16; y++) {
      for(let x=0; x<16; x++) {
        numberindex=Math.floor((x+y*16)/16)
        if(numberindex<=this.props.numbers.length) {
          let mask = 0xffff & ((2**depth)-1)
          pixels.push(
            <rect x={x*20*depth} y={y*20*depth} width={20*depth} height={20*depth} style={{fill:cindex[depth][(this.props.numbers[numberindex]>>(x*depth))&mask]}} />
          )
        }
      }
    }
    return(
      <svg viewBox={"0 0 320 320"} width="320px" height="320px" style={{backgroundColor:"black",marginRight:"16px"}}>
        {pixels}
      </svg>
    )
  }
}

export default DisplayCanvas
