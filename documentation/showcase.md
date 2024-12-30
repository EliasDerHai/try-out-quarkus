## Showcase

This is a quick and dirty collection of some api interaction to show that there is some functionality. 
The showcase snippets assume that you got the app booted up on localhost under the default port.

If you didn't get things running yet, have a look at [getting-started](./getting-started.md).


1. Pizzas

- create
```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"Gorgonzola","price":11.95}' \
  http://localhost:8080/pizza
```
- read all 
```bash
curl --location 'http://localhost:8080/pizza/'
```

- read one 
```bash
curl --location 'http://localhost:8080/pizza/1'
```

2. Persons

- create
```bash
curl --location 'http://localhost:8080/person/' \
--header 'Content-Type: application/json' \
--data '
{
"firstName": "John",
"lastName": "Doe",
"phoneNumber": 1111111111,
"userGroup": "customer"
}'
```

- read all
```bash
curl --location 'http://localhost:8080/person'
```

- read one
```bash
# json parsing ala chat-gpt or just copy the random uuid from the response of the /person endpoint
FIRST_ID=$(curl --silent --location 'http://localhost:8080/person' | grep -o '"id":"[^"]*"' | head -n 1 | sed 's/"id":"//;s/"//')
curl --location "http://localhost:8080/person/${FIRST_ID}"
```

3. Orders

- place order 
```bash
curl --location 'http://localhost:8080/order' \
--header 'Content-Type: application/json' \
--data '  {
    "pizzaIdWithQuantity": {  
        "1": 2,
        "2": 3
      },
    "destination": {
      "streetName": "street",
      "houseNumber": "2",
      "zipCode": 19232,
      "city": "RGB"
    },
    "orderer": {
      "firstName": "John",
      "lastName": "Doe",
      "userGroup": "customer"
    }
  }'
```

- fetch all
```bash
curl --location 'http://localhost:8080/order'
```

- fetch one
```bash
curl --location 'http://localhost:8080/order/1'
```