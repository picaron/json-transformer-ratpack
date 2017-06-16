# JSON Transformer Function Service (FaaS)

This tiny function service offers capabilites for transforming a JSON structure to another following a query expression.

The function uses the excellent https://github.com/eiiches/jackson-jq Java library to do the transformation.

## How to use

Simple perform a `POST /transform` with a JSON payload following the following structure :

    {
      "input": ... (any array or object as input data)
      "query": "the jq query string"
    }

To experiment with an input JSON and test the result of your query on it, use this online tool : https://jqplay.org

For the jq manual, see https://stedolan.github.io/jq/manual/

### Example

Request :

```
POST /transform HTTP/1.1
Content-Type: application/json; charset=utf-8
Host: localhost:5050
Connection: close
Content-Length: 6942

{
    "input": [
        {
            "manufacturer": "Porsche",
            "model": "911",
            "price": 135000,
            "wiki": "http://en.wikipedia.org/wiki/Porsche_997",
            "img": "2004_Porsche_911_Carrera_type_997.jpg"
        },
        {
            "manufacturer": "Nissan",
            "model": "GT-R",
            "price": 80000,
            "wiki": "http://en.wikipedia.org/wiki/Nissan_Gt-r",
            "img": "250px-Nissan_GT-R.jpg"
        },
        {
            "manufacturer": "BMW",
            "model": "M3",
            "price": 60500,
            "wiki": "http://en.wikipedia.org/wiki/Bmw_m3",
            "img": "250px-BMW_M3_E92.jpg"
        },
        {
            "manufacturer": "Audi",
            "model": "S5",
            "price": 53000,
            "wiki": "http://en.wikipedia.org/wiki/Audi_S5#Audi_S5",
            "img": "250px-Audi_S5.jpg"
        },
        {
            "manufacturer": "Audi",
            "model": "TT",
            "price": 40000,
            "wiki": "http://en.wikipedia.org/wiki/Audi_TT",
            "img": "250px-2007_Audi_TT_Coupe.jpg"
        }
    ],
    "queryExpression": "( .[] |= ( { (.manufacturer + \" \" + .model): .img } ) ) | add"
}
```

Response:

```
{
    "Porsche 911": "2004_Porsche_911_Carrera_type_997.jpg",
    "Nissan GT-R": "250px-Nissan_GT-R.jpg",
    "BMW M3": "250px-BMW_M3_E92.jpg",
    "Audi S5": "250px-Audi_S5.jpg",
    "Audi TT": "250px-2007_Audi_TT_Coupe.jpg"
}
```

## Run locally

    ./gradlew run --continuous
    
Note: with `--continuous` Gradle will hot-reload any changes in the code.

## Build fat JAR

    ./gradlew shadowJar
