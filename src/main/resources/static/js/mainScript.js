const menuElements = document.getElementsByClassName("menu-link");
let accountElements;
let targetAccount;
let cardElements;
let targetCard;

fetch("/api/v1/users/passport")
.then(data => data.json())
.then(passport => {
  const tempElem = document.querySelector("#mainp");
  let newElem = document.createElement('p');
  newElem.innerHTML = "Identity code: " + passport.identityCode;
  tempElem.after(newElem);

  newElem = document.createElement('p');
  newElem.innerHTML = "Date of birth: " + passport.dob.substring(0, 10);
  tempElem.after(newElem);

  newElem = document.createElement('p');
  newElem.innerHTML = "Succession: " + passport.sns.succession;
  tempElem.after(newElem);

  newElem = document.createElement('p');
  newElem.innerHTML = "Name: " + passport.sns.name;
  tempElem.after(newElem);

  newElem = document.createElement('p');
  newElem.innerHTML = "Surname: " + passport.sns.surname;
  tempElem.after(newElem);
});

function onMenuClick(event) {
  for (let i = 0; i < menuElements.length; i++) {
    menuElements[i].style.borderStyle = "none";
    menuElements[i].style.borderWidth = "0 0 0 0";
  }

  const menuElem = event.currentTarget;
  menuElem.style.borderBottomColor = "white";
  menuElem.style.borderStyle = "solid";
  menuElem.style.borderWidth = "0 0 3px 0";
}

function createAccountVisual(element) {
  let accHolder = document.querySelector(".account-holder");

  let accountView = document.createElement("div");
  accountView.classList.add("account-elem");

  let id = document.createElement("p");
  id.style.display = "none";
  id.innerHTML = element.id;
  accountView.append(id);

  let text = document.createElement("p");
  text.innerHTML = element.iban;
  accountView.append(text);

  text = document.createElement("p");
  text.innerHTML = element.number;
  accountView.append(text);

  let moneyWrapper = document.createElement("div");
  moneyWrapper.classList.add("money-wrapper");

  text = document.createElement("p");
  text.innerHTML = element.money;
  moneyWrapper.append(text);

  text = document.createElement("p");
  text.innerHTML = element.currencyType;
  moneyWrapper.append(text);

  accountView.append(moneyWrapper);
  accHolder.append(accountView);
  accountView.addEventListener('click', onAccountPick);
  return accountView;
}

function createAccount() {
  const data = {};
  data.currencyType = document.querySelector(".create-account select").value;
  console.log(JSON.stringify(data));
  fetch('api/v1/accounts/new', {
    method: 'POST',
    body: JSON.stringify(data),
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then(response => response.json())
  .then(json => createAccountVisual(json))
  .then(
      () => accountElements = document.getElementsByClassName("account-elem"));
}

function displayAccounts() {
  fetch('api/v1/accounts/user-attached')
  .then(data => data.json())
  .then(json => {
    const accHolder = document.querySelector(".account-holder");
    accHolder.innerHTML = "";
    json.forEach(element => {
      createAccountVisual(element);
    });
  });
}

function onAccountPick(event) {
  targetAccount = event.currentTarget;
  if (!(targetAccount.style.borderStyle === "solid")) {
    for (let i = 0; i < accountElements.length; i++) {
      accountElements[i].style.borderStyle = "none";
      accountElements[i].style.borderWidth = "0";
    }

    targetAccount.style.borderColor = "black";
    targetAccount.style.borderStyle = "solid";
    targetAccount.style.borderWidth = "2px";
  } else {
    targetAccount.style.borderStyle = "none";
    targetAccount.style.borderWidth = "0";
    targetAccount = undefined;
  }
}

function createCardVisual(element) {
  let cardHolder = document.querySelector(".card-holder");

  let cardView = document.createElement("div");
  cardView.classList.add("card-elem");

  let id = document.createElement("p");
  id.style.display = "none";
  id.innerHTML = element.id;
  cardView.append(id);

  let cardViewFront = document.createElement("div");
  cardViewFront.classList.add("card-front");

  let cardViewBack = document.createElement("div");
  cardViewBack.classList.add("card-back");

  let text = document.createElement("p");
  text.innerHTML = element.number;
  cardViewFront.append(text);

  text = document.createElement("p");
  text.innerHTML = 'VALID THRU: ' + element.expirationTime.substring(0, 7);
  cardViewFront.append(text);

  text = document.createElement("p");
  text.innerHTML = 'CVV: ' + element.cvv;
  cardViewBack.append(text);

  text = document.createElement("p");
  text.innerHTML = 'PIN: ' + element.pin;
  cardViewBack.append(text);

  cardView.append(cardViewFront, cardViewBack);
  cardHolder.append(cardView);
  cardView.addEventListener('click', onCardPick);
}

function createCard() {
  const data = {};
  const account = {};
  const card = {};

  if (targetAccount) {
    account.iban = targetAccount.getElementsByTagName('p')[1].innerHTML;
    account.currencyType = "USD";
    let today = new Date();
    const selector = document.querySelector(".create-card select");
    card.expirationTime = (today.getFullYear() + Number(
            selector.value.charAt(0)))
        + '-0' + (today.getMonth() + 1)
        + '-' + ((today.getDate() < 10) ? '0' + today.getDate() : today.getDate())
        + 'T00:00';

    data.accountDto = account;
    data.cardDto = card;
    console.log(JSON.stringify(data));
    fetch('/api/v1/cards/new', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(ans => ans.json())
    .then(json => createCardVisual(json));
  } else {
    alert('Please, choose an account');
  }
}

function displayCards() {
  fetch('api/v1/cards/user-attached')
  .then(data => data.json())
  .then(json => {
    const accHolder = document.querySelector(".card-holder");
    accHolder.innerHTML = "";
    json.forEach(element => createCardVisual(element));
  })
  .then(() => cardElements = document.getElementsByClassName("card-elem"));
}

function onCardPick(event) {
  targetCard = event.currentTarget;
  if (!(targetCard.style.borderStyle === "solid")) {
    for (let i = 0; i < cardElements.length; i++) {
      cardElements[i].style.borderStyle = "none";
      cardElements[i].style.borderWidth = "0";
    }

    targetCard.style.borderColor = "black";
    targetCard.style.borderStyle = "solid";
    targetCard.style.borderWidth = "2px";
  } else {
    targetCard.style.borderStyle = "none";
    targetCard.style.borderWidth = "0";
    targetCard = undefined;
  }
}

function sendMoneyFromAccount() {
  const data = {};
  if (targetAccount) {
    data.fromAccountId = targetAccount.firstChild.innerHTML;
    data.toAccountNumber = document.getElementById("dest-account-num").value;

    if (data.toAccountNumber.match('^\\d{16}$').length === null) {
      alert("Destination account field is invalid");
      return;
    }
    data.amount = document.getElementById("amount-of-money").value;
    if (data.amount.match('^\\d+$') === null) {
      alert("Amount field is invalid");
      return;
    }
    fetch('api/v1/transaction/accounts', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => {
      console.log(response.status);
      if (response.status === 400) {
        alert("Bad request, try to input another values");
      } else if (response.status === 204) {
        alert("Your money were successfully transferred");
        document.getElementById("amount-of-money").value = "";
        document.getElementById("dest-account-num").value = "";
        displayAccountTransactions();
        displayAccounts();
      }
      return response.body;
    })
    .then(json => console.log(json))
    .catch(e => console.log(e.toString()));
    console.log(JSON.stringify(data));
  } else {
    alert('Please, choose an account');
  }
}

function showExRates() {
  let holder = document.querySelector(".rate-holder");
    fetch('api/v1/currency')
    .then(response => {
      if (response.status === 200) {
        return response.json();
      } else {
        alert("Failed to load exchange rates");
      }
    })
    .then(json => {
      holder.innerHTML = "";
      console.log(json);
      let header = document.createElement('h2');
      header.innerHTML = "Exchange rates on " + json.updatedAt.substring(0, 10);
      holder.append(header);

      let text = document.createElement('p');
      text.innerHTML = "USD/EUR: " + json.quotes.USDEUR;
      holder.append(text);

      text = document.createElement('p');
      text.innerHTML = "USD/BYN: " + json.quotes.USDBYN;
      holder.append(text);

      text = document.createElement('p');
      text.innerHTML = "USD/RUB: " + json.quotes.USDRUB;
      holder.append(text);
    })
    .catch(e => console.log(e.toString()));
}

function sendMoneyFromCard() {
  const data = {};
  if (targetCard) {
    data.fromCardId = targetCard.firstChild.innerHTML;
    data.toCardNumber = document.getElementById("dest-card-num").value;
    if (data.toCardNumber.match('^\\d{16}$').length === null) {
      alert("Destination card field is invalid");
      return;
    }
    data.amount = document.getElementById("amount-of-money").value;
    if (data.amount.match('^\\d+$').length === null) {
      alert("Amount field is invalid");
      return;
    }
    console.log(JSON.stringify(data));
    fetch('api/v1/transaction/cards', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => {
      if (response.status === 400) {
        alert("Bad request, try to input another values");
      } else if (response.status === 204) {
        alert("Your money were successfully transferred");
        document.getElementById("amount-of-money").value = "";
        document.getElementById("dest-card-num").value = "";
        displayCardTransactions();
        displayAccounts();
      }
    });
  } else {
    alert('Please, choose a card');
  }
}

function displayCardTransactions() {
  fetch('api/v1/transaction/cards')
  .then(response => {
    if (response.status === 200) {
      return response.json();
    } else {
      throw new Error('Something goes really wrong');
    }
  })
  .then(json => {
    document.querySelector(".card-transactions").innerHTML
        = "<h3>Card transactions:</h3>";
    json.forEach(tInfo => createCardTransactionView(tInfo));
  })
  .catch(e => console.log(e.toString()));
}

function displayAccountTransactions() {
  fetch('api/v1/transaction/accounts')
  .then(response => {
    if (response.status === 200) {
      return response.json();
    } else {
      throw new Error('Something goes really wrong');
    }
  })
  .then(json => {
      document.querySelector(".account-transactions").innerHTML
          = "<h3>Account transactions:</h3>";
      json.forEach(tInfo => createAccountTransactionView(tInfo));
  })
  .catch(e => console.log(e.toString()));
}

function createAccountTransactionView(element) {
  let holder = document.querySelector(".account-transactions");
  const text = document.createElement('p');
  text.innerHTML = "FROM: " + element.srcAccountNumber +
      "  TO: " + element.destAccountNumber +
      "  " + element.amount + element.currencyType +
      " AT " + element.beingAt.substring(0, 10) + " " + element.beingAt.substring(12, 16);

  holder.append(text);
}

function createCardTransactionView(element) {
  let holder = document.querySelector(".card-transactions");
  const text = document.createElement('p');
  text.innerHTML = "FROM: " + element.srcCardNumber +
      "  TO: " + element.destCardNumber +
      "  " + element.amount + element.currencyType +
      " AT " + element.beingAt.substring(0, 10) + " " + element.beingAt.substring(12, 16);

  holder.append(text);
}

accountElements = document.getElementsByClassName("account-elem");

for (let i = 0; i < menuElements.length; i++) {
  menuElements[i].addEventListener('click', onMenuClick);
}

menuElements[0].style.borderBottomColor = "white";
menuElements[0].style.borderStyle = "solid";
menuElements[0].style.borderWidth = "0 0 3px 0";

document.querySelector("#send-to-account-button").addEventListener('click',
    sendMoneyFromAccount);
document.querySelector("#send-to-card-button").addEventListener('click',
    sendMoneyFromCard);
document.querySelector("#create-account-button").addEventListener('click',
    createAccount);
document.querySelector("#create-card-button").addEventListener('click',
    createCard);

displayCards();
displayAccounts();
displayCardTransactions();
displayAccountTransactions();
showExRates();