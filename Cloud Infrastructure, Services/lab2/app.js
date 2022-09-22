const express = require('express')
const mysql = require('mysql')
const fs = require("fs")
const path = require("path");

let app = express()

app.use(express.json(), express.urlencoded({ extended: false }))

app.use(express.static(path.join(__dirname,"./build")))

let con = mysql.createConnection({
  host: "test2.ca7y94rlpxla.eu-west-1.rds.amazonaws.com",
  database: "ebdb",
  user: "root",
  password: "password"
}, {multipleStatements:true})

let e = "no error :)"
con.connect((err) => {
  if (err) e = err
  else{
    handleInits("./drop.sql")
    handleInits("./create.sql")
    handleInits("./insert.sql")
  }
})

function handleInits(filename){
  const init = fs.readFileSync(filename).toString()
    console.log(filename)
    con.query(init, (err, result)=>{
      if(err) e = err
    })
}

// app.get("/",(req, res) => {
//   res.send("<h1>Hello world</h1><a href='/data'>To data</a>")
// })

app.get("/data", (req, res) => {
  let data = ""
  con.query("select * from ebdb.inflation", async (err, result, fields) => {
    if (err) e =  err
    console.log(result)
    data = await result
    // res.send(`
    //   Connection Status: ${e}
    //   ${data}
    // `)
    res.send(data)
  })
})


app.get("/data/:country", (req, res) =>{
  let data = ""
  con.query(`select * from ebdb.inflation where country = "${country}"`, async (err, result, fields) => {
    if (err) e =  err
    console.log(result)
    data = await result
    res.json(data)
  })
})

app.listen(8080, ()=> {
  console.log("listening on port 8080...")
})