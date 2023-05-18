
class ButtonClass extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {sessionLength: 25, breakLength: 5, second: "00", minute: 25, play:0, type: "Session"};

        this.decSessionLength = this.decSessionLength.bind(this);
        this.incSessionLength = this.incSessionLength.bind(this);
        this.decBreakLength = this.decBreakLength.bind(this);
        this.incBreakLength = this.incBreakLength.bind(this);
        this.runCountDown = this.runCountDown.bind(this);
        this.play = this.play.bind(this);
        this.pause = this.pause.bind(this);
    }

    incSessionLength = () =>{
        if(this.state.play != 1 && this.state.sessionLength < 60){
            if(this.state.minute < 9){
                this.setState({sessionLength: this.state.sessionLength + 1, minute: "0" + (this.state.sessionLength + 1), second: "00", type: "Session"});
            }
            else{
                this.setState({sessionLength: this.state.sessionLength + 1, minute: this.state.sessionLength + 1, second: "00", type: "Session"});
            }
        }
    }

    decSessionLength = () =>{
        if(this.state.play != 1 && this.state.sessionLength > 1){
            if(this.state.minute <= 10){
                this.setState({sessionLength: this.state.sessionLength - 1, minute: "0" + (this.state.sessionLength - 1), second: "00", type: "Session"});
            }
            else{
                this.setState({sessionLength: this.state.sessionLength - 1, minute: this.state.sessionLength - 1, second: "00", type: "Session"});
            }
        }
    }

    incBreakLength = () =>{
        if(this.state.breakLength < 60){
            this.setState({breakLength: this.state.breakLength + 1});
        }
    }

    decBreakLength = () =>{
        if(this.state.breakLength > 1){
            this.setState({breakLength: this.state.breakLength - 1});
        }
    }

    runCountDown = () =>{
        if(this.state.minute == 0 && this.state.second == 0){
            if(this.state.type == "Session"){
                this.setState({minute: this.state.breakLength, type: "Break"});
            }
            else{
                this.setState({minute: this.state.sessionLength, type: "Session"});
            }
            var audio = document.getElementById("beep");
            audio.play();

        }
        if(this.state.play == 1){
            if(this.state.second > 0){
                if(this.state.second <= 10){
                    this.setState({second: "0" + (this.state.second - 1)});
                }
                else{
                    this.setState({second: this.state.second - 1});
                }
            }
            else{
                if(this.state.minute <= 10){
                    this.setState({minute: "0" + (this.state.minute - 1), second: "59"});
                }
                else{
                    this.setState({minute: this.state.minute - 1, second: "59"});
                }
            }
        }
    }

    play = () =>{
        this.setState({play: 1});
    }

    pause = () =>{
        this.setState({play: 0});
    }

    redo = () =>{
        this.setState({minute: this.state.sessionLength, second: "00", play: 0, type: "Session"});
    }

    componentDidMount(){
            setInterval(() => {
            this.runCountDown()
            },1000)
    }

    render(){
        return(
           <div>
               <div id="button-area">
                   <div id="break-label">
                       Break Length
                       <button onClick={() => this.incBreakLength()} id="break-increment" class="fa fa-arrow-up"></button>
                       {this.state.breakLength}
                       <button onClick={() => this.decBreakLength()} id="break-decrement" class="fa fa-arrow-down"></button>
                    </div>
                   <div id="session-label">
                       Session Length
                       <button onClick={() => this.incSessionLength()} id="session-increment" class="fa fa-arrow-up"></button>
                       {this.state.sessionLength}
                       <button onClick={() => this.decSessionLength()} id="session-decrement" class="fa fa-arrow-down"></button>
                    </div>
               </div>
               <div id="clockRadius">
                    <div id="clockTime">
                        <div class="timer-label">{this.state.type}</div>
                        <div id="time-left">{this.state.minute}:{this.state.second}</div>
                        <audio id="beep" src="https://assets.mixkit.co/sfx/preview/mixkit-repeating-arcade-beep-1084.mp3"></audio>
                    </div>
               </div>
               <div class="center">
                  <button id="start_stop" onClick={() => this.play()} class="fa fa-play fa-2x"></button>
                  <button onClick={() => this.pause()} class="fa fa-pause fa-2x"></button>
                  <button id="reset" onClick={() => this.redo()} class="fa fa-refresh fa-2x"></button>
               </div>
           </div>
        )
    }
}

class Clock extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {};
    }

    render(){
        return(
           <div>
               <div id="title">25 + 5 Clock</div>
               <ButtonClass />
           </div>
        )
    }
}

ReactDOM.render(
    <Clock />,
    document.getElementById('clock')
);