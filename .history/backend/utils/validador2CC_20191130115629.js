/**
 * Função para validar um Cartão do Cidadão (ex : 10890919-7-ZY3)
 *
 * @param   string $number  Número a verificar
 * 
 * @return  bool   	    Booleen que determina se o número é válido ou não.
 */
function check_pt_cc_number(number) {
	let letter_value = { A: 10, B: 11, C: 12, D: 13, E: 14, F: 15, G: 16, H: 17, I: 18, J: 19, K: 20, L: 21, M: 22, N: 23, O: 24, P: 25, Q: 26, R: 27, S: 28, T: 29, U: 30, V: 31, W: 32, X: 33, Y: 34, Z: 35};
    let cc_number = number.replace(/-|\s/g, ''); // remove space and -
	cc_number = cc_number.toUpperCase();
	cc_number = [...cc_number];
	cc_number = cc_number.reverse();
	cc_number[1] = letter_value[cc_number[1]];
	cc_number[2] = letter_value[cc_number[2]];
	let sum = 0;
  let dum = 0;
  
  cc_number.forEach((k, v) => {
    if ( k % 2 == 0) {
			dum = parseInt(v);
		}
		else {
			dum = parseInt(v) * 2;
			if (dum >= 10)
				dum -= 9;
		}
		sum += dum;
		console.log('k : '+ k + ' | sum : '+ sum);
  })
	 
	return (sum % 10 == 0) ? true : false;
}


module.exports = check_pt_cc_number;