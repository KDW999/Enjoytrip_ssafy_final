export default async function require(path) {
	let _module = window.module;
	window.module = {};
	await import(path);
	let exports = window.module.exports;
	window.module = _module; // restore global
	return exports;
}
