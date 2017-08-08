const config = require("./scalajs.webpack.config");

// expose PIXI globally
// ref: https://scalacenter.github.io/scalajs-bundler/cookbook.html#global-namespace
const globalModules = {
	"pixi.js": "PIXI"
}

Object.keys(config.entry).forEach((key) => {
	// Prepend each entry with the globally exposed JS dependencies
	config.entry[key] = Object.keys(globalModules).concat(config.entry[key]);
});

// Globally expose the JS dependencies
config.module.loaders = Object.keys(globalModules).map((pkg) => {
	return {
		test: require.resolve(pkg),
		loader: "expose-loader?" + globalModules[pkg]
	};
});

module.exports = config;

