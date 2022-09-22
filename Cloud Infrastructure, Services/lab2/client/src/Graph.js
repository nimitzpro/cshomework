import React,{ useState, useEffect } from "react"
import { Line } from 'react-chartjs-2'
import Chart from 'chart.js/auto'
import axios from 'axios'



export default function Graph(){
    
    let [data, setData] = useState({})

    useEffect(async ()=>{
        console.log("fetching data")
        let d = {
            dates:[],
            countries:{},
            points: []
          }
        
        let response = await axios.get("/data")
        let res = response.data
      
        console.log(res)
        for(let a of res){
          if(!d.dates.includes(a.date)){
            d.dates.push(a.date)
          }
          if (!d.countries[a.country]) {
            d.countries[a.country] = []
          }
          d.countries[a.country].push(a.rate)
        }

        for(let b of Object.keys(d.countries)){
          let l = []
          for(let i of d.countries[b]){
            l.push(i)
          }
          d.points.push({label: b, data: l})
        }

        console.log("parsed data", d)
    

    setData(d)
    
    console.log("data set", data)
  
}, [])

    let [labels, setLabels] = useState(data.hasOwnProperty("dates") ? data.dates : null)
    let [sets, setSets] = useState(data.hasOwnProperty("countries") ? data.points : null)

    useState(()=>{
        setLabels(data.hasOwnProperty("dates") ? data.dates : null)
        setSets(data.hasOwnProperty("countries") ? data.points : null)
    }, [data])

    return(data.hasOwnProperty("points") ?
        <Line 
            data={{
                labels: labels,
                datasets:  sets
              }}

            options={{
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        position: "left"
                    }
                }
            }}
        /> : <></>
        
    )
}