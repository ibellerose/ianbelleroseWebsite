

class ButtonClass extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {calcVariable: '0', mathStatement: ''};

        //binding method
        this.addToScreen = this.addToScreen.bind(this);
    }

    addToScreen = (btnPushed) => {
        if((this.state.calcVariable.length >= 12 && btnPushed >=0)){
        }
        else if(this.state.mathStatement.includes('=') && btnPushed >=0){
            this.setState({calcVariable: btnPushed, mathStatement: btnPushed})
        }
        else{
            if(btnPushed == "AC"){
                this.setState({calcVariable: '0', mathStatement: ''})
            }
            else if((btnPushed >= 0 && btnPushed <= 9) || (btnPushed == "." && !this.state.calcVariable.includes('.')))
            {
                if((this.state.calcVariable == '0' || this.state.calcVariable == '+' || this.state.calcVariable == '-' || this.state.calcVariable == 'x' || this.state.calcVariable == '/') && btnPushed == "."){
                    if(this.state.calcVariable != '0' || btnPushed != '.'){
                        this.setState({calcVariable : "0.", mathStatement : this.state.mathStatement + "0."})
                    }
                    else{
                        this.setState({calcVariable : "0.", mathStatement : this.state.mathStatement + "0."})
                    }
                }
                else if((this.state.calcVariable == '0' || this.state.calcVariable == '+' || this.state.calcVariable == '-' || this.state.calcVariable == 'x' || this.state.calcVariable == '/') && btnPushed != "."){
                    if(this.state.calcVariable != '0' || btnPushed != '0'){
                        this.setState({calcVariable : btnPushed, mathStatement : this.state.mathStatement + btnPushed})
                    }
                }
                else{
                    this.setState({calcVariable : this.state.calcVariable + btnPushed, mathStatement : this.state.mathStatement + btnPushed})
                }
            }
            else if(btnPushed == "+")
            {
                if(this.state.calcVariable != '+' && this.state.calcVariable != 'x' && this.state.calcVariable != '/' && this.state.calcVariable != '-'){
                    this.setState({calcVariable : '+', mathStatement : this.state.mathStatement + "+"})
                }
                else{
                    if(this.state.mathStatement.substring(this.state.mathStatement.length - 2, this.state.mathStatement.length) == '--'){
                        this.setState({calcVariable : '+', mathStatement : this.state.mathStatement.substring(0, this.state.mathStatement.length - 2) + "+"})
                    }
                    else{
                        let last2 = this.state.mathStatement.substring(this.state.mathStatement.length - 2, this.state.mathStatement.length);

                        if(last2 == '+-' || last2 == '*-' || last2 == '/-'){
                            this.setState({calcVariable : '+', mathStatement : this.state.mathStatement.substring(0, this.state.mathStatement.length - 2) + "+"})
                        }
                        else{
                            this.setState({calcVariable : '+', mathStatement : this.state.mathStatement.substring(0, this.state.mathStatement.length - 1) + "+"})
                        }
                    }
                }
                
                if(this.state.mathStatement.indexOf('=') >= 0){
                    this.setState({mathStatement : this.state.mathStatement.substring(this.state.mathStatement.indexOf('=')+1, this.state.mathStatement.length) + "+"})
                }

            }
            else if(btnPushed == "-")
            {
                let last2 = this.state.mathStatement.substring(this.state.mathStatement.length - 2, this.state.mathStatement.length);

                if(this.state.calcVariable != '+' && this.state.calcVariable != 'x' && this.state.calcVariable != '/' && last2 != '--' && last2 != '*-' && last2 != '/-' && last2 != '+-'){
                    this.setState({calcVariable : '-', mathStatement : this.state.mathStatement + "-"})
                }
                else{
                    last2 = this.state.mathStatement.substring(this.state.mathStatement.length - 2, this.state.mathStatement.length);

                    if(last2 != '--' && last2 != '*-' && last2 != '/-' && last2 != '+-'){
                        this.setState({calcVariable : '-', mathStatement : this.state.mathStatement + "-"})
                    }
                }
                if(this.state.mathStatement.indexOf('=') >= 0){
                    this.setState({mathStatement : this.state.mathStatement.substring(this.state.mathStatement.indexOf('=')+1, this.state.mathStatement.length) + "-"})
                }
            }
            else if(btnPushed == "x")
            {
                if(this.state.calcVariable != '+' && this.state.calcVariable != 'x' && this.state.calcVariable != '/' && this.state.calcVariable != '-'){
                    this.setState({calcVariable : 'x', mathStatement : this.state.mathStatement + "*"})
                }
                else{
                    if(this.state.mathStatement.substring(this.state.mathStatement.length - 2, this.state.mathStatement.length) == '--'){
                        this.setState({calcVariable : 'x', mathStatement : this.state.mathStatement.substring(0, this.state.mathStatement.length - 2) + "*"})
                    }
                    else{
                        let last2 = this.state.mathStatement.substring(this.state.mathStatement.length - 2, this.state.mathStatement.length);

                        if(last2 == '+-' || last2 == '*-' || last2 == '/-'){
                            this.setState({calcVariable : 'x', mathStatement : this.state.mathStatement.substring(0, this.state.mathStatement.length - 2) + "*"})
                        }
                        else{
                            this.setState({calcVariable : 'x', mathStatement : this.state.mathStatement.substring(0, this.state.mathStatement.length - 1) + "*"})
                        }
                    }
                }
                if(this.state.mathStatement.indexOf('=') >= 0){
                    this.setState({mathStatement : this.state.mathStatement.substring(this.state.mathStatement.indexOf('=')+1, this.state.mathStatement.length) + "*"})
                }
            }
            else if(btnPushed == "/")
            {
                if(this.state.calcVariable != '+' && this.state.calcVariable != 'x' && this.state.calcVariable != '/' && this.state.calcVariable != '-'){
                    this.setState({calcVariable : '/', mathStatement : this.state.mathStatement + "/"})
                }
                else{
                    if(this.state.mathStatement.substring(this.state.mathStatement.length - 2, this.state.mathStatement.length) == '--'){
                        this.setState({calcVariable : '/', mathStatement : this.state.mathStatement.substring(0, this.state.mathStatement.length - 2) + "/"})
                    }
                    else{
                        let last2 = this.state.mathStatement.substring(this.state.mathStatement.length - 2, this.state.mathStatement.length);

                        if(last2 == '+-' || last2 == '*-' || last2 == '/-'){
                            this.setState({calcVariable : '/', mathStatement : this.state.mathStatement.substring(0, this.state.mathStatement.length - 2) + "/"})
                        }
                        else{
                            this.setState({calcVariable : '/', mathStatement : this.state.mathStatement.substring(0, this.state.mathStatement.length - 1) + "/"})
                        }
                    }
                }
                if(this.state.mathStatement.indexOf('=') >= 0){
                    this.setState({mathStatement : this.state.mathStatement.substring(this.state.mathStatement.indexOf('=')+1, this.state.mathStatement.length) + "/"})
                }
            }
            else if(btnPushed == "=")
            {
                let mathExp = this.state.mathStatement;
                let finalExp = mathExp.replace('--', '+');

                this.setState({calcVariable: eval(finalExp), mathStatement : this.state.mathStatement + "=" + eval(finalExp)})
            }
        }
    }

    render(){
        return(
            <div>
                <div class="screen">
                    <div id="fullStatementDisplay">
                        {this.state.mathStatement}
                    </div>
                    <div id="display">
                        {this.state.calcVariable}
                    </div>
                </div>

                <div class="numberButtonWrapper">
                    <button onClick={() => this.addToScreen("AC")} class="numberButton" id="clear">AC</button>
                    <button onClick={() => this.addToScreen("/")} class="mathButton" id="divide">/</button>
                    <button onClick={() => this.addToScreen("x")} class="mathButton" id="multiply">x</button>
                    <button onClick={() => this.addToScreen("7")} class="numberButton" id="seven">7</button>
                    <button onClick={() => this.addToScreen("8")} class="numberButton" id="eight">8</button>
                    <button onClick={() => this.addToScreen("9")} class="numberButton" id="nine">9</button>
                    <button onClick={() => this.addToScreen("-")} class="mathButton" id="subtract">-</button>
                    <button onClick={() => this.addToScreen("4")} class="numberButton" id="four">4</button>
                    <button onClick={() => this.addToScreen("5")} class="numberButton" id="five">5</button>
                    <button onClick={() => this.addToScreen("6")} class="numberButton" id="six">6</button>
                    <button onClick={() => this.addToScreen("+")} class="mathButton" id="add">+</button>
                    <button onClick={() => this.addToScreen("1")} class="numberButton" id="one">1</button>
                    <button onClick={() => this.addToScreen("2")} class="numberButton" id="two">2</button>
                    <button onClick={() => this.addToScreen("3")} class="numberButton" id="three">3</button>
                    <button onClick={() => this.addToScreen("=")} class="numberButton" id="equals">=</button>
                    <button onClick={() => this.addToScreen("0")} class="numberButton" id="zero">0</button>
                    <button onClick={() => this.addToScreen(".")} class="numberButton" id="decimal">.</button>
                </div>
            </div>
        )
    }
}

class Calculator extends React.Component{
    constructor(props){
        super(props);
        
        this.state = {};
    }

    render(){
        return(
           <div>
               <ButtonClass />
           </div>
        )
    }
}

ReactDOM.render(
    <Calculator />,
    document.getElementById('calculator')
);