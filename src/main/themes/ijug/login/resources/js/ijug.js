function selectIdP(e) {
	const option = document.querySelector('option[value="' + e.value + '"]');
	const url = option.dataset.url;
	const target = document.getElementById('btnLogin');
	target.href = url;
	if (url) {
		target.classList.remove('pf-m-disabled');
	} else {
		target.classList.add('pf-m-disabled');
	}
}
