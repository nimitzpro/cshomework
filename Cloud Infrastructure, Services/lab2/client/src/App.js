import React, {useState, useEffect} from 'react'
import './App.css'
import Graph from './Graph'

export default function App() {

  return (
    <div className="App">
      <h1>Welcome to my page on inflation in Europe</h1>
      <p>The data for HCIP (Harmonised Index of Consumer Prices), taken from the <a href="https://www.ecb.europa.eu/stats/macroeconomic_and_sectoral/hicp/html/index.en.html">European Central Bank</a>, is used to measure consumer price inflation</p>
      <section>
        {/* <Graph /> */}
        <a href="/data">[graph]</a>
      </section>
    </div>
  )
}