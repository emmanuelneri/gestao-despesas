module.exports = function(grunt) {

	grunt.initConfig({
		copy: {
			project: {
				expand: true,
				cwd: '.',
				src: ['**', '!Gruntfile.js', '!package.json', '!bower.json'],
				dest: 'dist'
			},
			fonts: {
				expand: true,
				cwd: 'app/lib/components-font-awesome/fonts/',
				src: ['**'],
				dest: 'dist/app/fonts'
			}
		},
		clean: {
			dist: {
				src: 'dist'
			}
		},
		usemin: {
			html: 'dist/app/index.html'
		},
		useminPrepare: {
			options: {
				root: 'dist/app',
				dest: 'dist/app'
			},
			html: 'dist/app/index.html'
		},
		ngAnnotate: {
			scripts: {
				expand: true,
				src: ['dist/app/js/**/*.js']
			}

		},
		jshint: {
			files: ['Gruntfile.js','server.js','config/*.js','app/js/**/*.js']
		}
	});

	grunt.registerTask('default', ['dist', 'minifica']);
	grunt.registerTask('dist', ['clean', 'copy']);
	grunt.registerTask('minifica', ['useminPrepare', 'ngAnnotate', 'jshint', 'concat', 'uglify', 'cssmin', 'usemin']);

	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-cssmin');
	grunt.loadNpmTasks('grunt-ng-annotate');
	grunt.loadNpmTasks('grunt-usemin');
	grunt.loadNpmTasks('grunt-contrib-jshint');
};
