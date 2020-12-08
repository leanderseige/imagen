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
      3: ["#000","#00f","#0f0","#0ff","#f00","#f0f","#ff0","#fff"]
    }
    let depth=this.props.colormode

    for(let y=0; y<16; y++) {
      for(let x=0; x<16; x++) {
        console.log({x:x,y:y})
        numberindex=Math.floor((x+y*16)/16)
        console.log(numberindex)
        if(numberindex<=this.props.numbers.length) {
          let mask = 0xffff & ((2**depth)-1)
          pixels.push(
            <rect x={x*20*depth} y={y*20*depth} width={20*depth} height={20*depth} style={{fill:cindex[depth][(this.props.numbers[numberindex]>>(x*depth))&mask]}} />
          )
        }
      }
    }
    return(
      <svg viewBox="0 0 320 320" width="320px" height="320px" style={{backgroundColor:"black"}}>
        {pixels}
      </svg>
    )
  }
}

export default DisplayCanvas
