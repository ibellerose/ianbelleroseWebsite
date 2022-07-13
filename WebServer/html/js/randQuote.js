const quoteCollection = [{quote: "quote 1 is this quote that is very long so that I can test out how the app works with a quote that is long", author: "Ian Bellerose"},{quote: "quote 2", author: "Ian Bell"},{quote: "quote 3", author: "Ian B"}];

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