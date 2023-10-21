function load() {
    x = document.querySelectorAll('[name="r"]')
    for (let a = 0; a < x.length; a++) {
        x[a].hidden = true
    }
    
    tbt = document.querySelectorAll('.colTitulo')
    tbr = document.querySelectorAll('#col')
   
    let num = Math.round(100 / tbt.length) - 1
    
   	for (let a = 0; a < tbr.length; a++) {
		 tbr[a].style.width = `${num}%` 
		 tbr[a].style.marginLeft = `${(100 - (num * tbt.length)) / (tbt.length * 2)}%`
		 tbr[a].style.marginRight = `${(100 - (num * tbt.length)) / (tbt.length * 2)}%`
	}
}