const quoteCollection = [{quote: "The journey of a thousand miles begins with one step.", author: "Lao Tzu"}, {quote: "That which does not kill us makes us stronger.", author: "Friedrich Nietzsche"},{quote: "Life is what happens when you’re busy making other plans.", author: "John Lennon"},{quote: "When the going gets tough, the tough get going.", author: "Joe Kennedy"},{quote: "You must be the change you wish to see in the world.", author: "Mahatma Gandhi"},{quote: "You only live once, but if you do it right, once is enough.", author: "Mae West"},{quote: "Tough times never last but tough people do.", author: "Robert H. Schuller"},{quote: "Whether you think you can or you think you can’t, you’re right.", author: "Henry Ford"},{quote: "Tis better to have loved and lost than to have never loved at all.", author: "Alrded Lord Tennyson"},{quote: "You miss 100 percent of the shots you never take.", author: "Wayne Gretzky"},{quote: "If you’re going through hell, keep going.", author: "Winston Churchill"},{quote: "Strive not to be a success, but rather to be of value.", author: "Albert Einstein"},{quote: "Twenty years from now you will be more disappointed by the things that you didn’t do than by the ones you did do.", author: "Mark Twain"},{quote: "Those who dare to fail miserably can achieve greatly.", author: "John F. Kennedy"},{quote: "Never let the fear of striking out keep you from playing the game.", author: "Babe Ruth"}];

class QuoteBox extends React.Component{
    constructor(props){
        super(props);
        const randNum = Math.floor(Math.random() * quoteCollection.length);
        this.state = {
            quote: quoteCollection[randNum].quote,
            author: quoteCollection[randNum].author
        };
        this.randomQuoteGenerator = this.randomQuoteGenerator.bind(this);
    }

    randomQuoteGenerator () {
        const randNum = Math.floor(Math.random() * quoteCollection.length);
        this.setState ({
            quote: quoteCollection[randNum].quote,
            author: quoteCollection[randNum].author
        });
    }

    render(){
        return (
            <div>
                <h1 id = "text">"{this.state.quote}"</h1>
                <h2 id = "author">-{this.state.author}</h2>
                <a id="tweet-quote" href = "twitter.com/intent/tweet">t</a>
                <button id = "new-quote" class="btn btn-default" onClick = {this.randomQuoteGenerator}>New Quote</button>
            </div>
        );
    }
}


ReactDOM.render(
    <QuoteBox />,
    document.getElementById('quote-box')
);