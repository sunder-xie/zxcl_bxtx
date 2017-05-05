var gulp = require('gulp'),
	rev = require('gulp-rev'),
	revCollector = require('gulp-rev-collector'),
	runSequence = require('run-sequence'),
	clean = require('gulp-clean'),
	cleanCSS = require('gulp-clean-css'),
	uglify = require('gulp-uglify'),
	pump = require('pump'),
	htmlclean = require('gulp-htmlclean');;

gulp.task('clean',function(){
	return gulp.src('dist',{read:false}).pipe(clean());
/*	return gulp.src('dist')
    .pipe(clean({force: true}))
    .pipe(gulp.dest('dist'));*/
});

gulp.task('clean_tmp',function(){
	return gulp.src('dist_tmp',{read:false}).pipe(clean());
/*	return gulp.src('dist')
    .pipe(clean({force: true}))
    .pipe(gulp.dest('dist'));*/
});
/*gulp.task('css',function(){
	return gulp.src('src/main/webapp.css')
		.pipe(csso())
		.pipe(rename(function(path){
			path.basename += ".min";
			path.exname = ".css";
		}))
		.pipe(rev())
		.pipe(gulp.dest('dist/admin/app/css'))
		.pipe(rev.manifest())
		.pipe(gulp.dest('dist/admin/rev/css'));
});*/
/*gulp.task('images', function (cb) {
    return gulp.src('src/main/webapp*.{jpg,png,gif,jpeg}')
        .pipe(rev())
        .pipe(gulp.dest('dist/'))
        .pipe( rev.manifest() )
        .pipe( gulp.dest( 'dist/rev/images/' ) );
});*/
gulp.task('minify-css', function() {
	  return gulp.src('src/main/webapp/**/*.css')
	    .pipe(cleanCSS({compatibility: 'ie8'}))
	    .pipe(gulp.dest('dist_tmp/'));
});
gulp.task('css',['minify-css'], function (cb) {
    return gulp.src('dist_tmp/**/*.css')
        .pipe(rev())
        .pipe(gulp.dest('dist/'))
        .pipe( rev.manifest() )
        .pipe( gulp.dest( 'dist/rev/css' ) );
});
/*gulp.task('rev_css',['css'],function(){
	return gulp.src(['dist/rev/*.json','dist/*.css'])
		.pipe(revCollector({
			replaceReved: true
		}))
		.pipe(gulp.dest('dist/'));
});*/
gulp.task('compress', function (cb) {
	  pump([
	        gulp.src('src/main/webapp/**/*.js'),
	        uglify(),
	        gulp.dest('dist_tmp/')
	    ],
	    cb
	  );
	});
gulp.task('scripts',['compress'], function () {
    return gulp.src(['dist_tmp/**/bxtx_virtual_keyboard.js',
                     'dist_tmp/**/comoperate.js',
                     'dist_tmp/**/flow_insurance.js',
                     'dist_tmp/**/flow_insurance1.js',
                     'dist_tmp/**/flow_insurance2.js',
                     'dist_tmp/**/pinchzoom.js',
                     'dist_tmp/**/ui_index.js',
                     'dist_tmp/**/upload_videomaterial.js',
                     'dist_tmp/**/swiper.js',
                     'dist_tmp/**/TouchSlide.1.1.js',
                     'dist_tmp/**/main.js',
                     'dist_tmp/**/add2home.js',
                     'dist_tmp/**/check.js',
                     'dist_tmp/**/common.js',
                     'dist_tmp/**/html5.js',
                     'dist_tmp/**/index.js',
                     'dist_tmp/**/init.js',
                     'dist_tmp/**/jiaohu.js',
                     'dist_tmp/**/main_new.js',
                     'dist_tmp/**/prettify.js',
                     'dist_tmp/**/WdatePicker.js',
                     'dist_tmp/**/yxMobileSlider.js',
                     'dist_tmp/**/requestAnimationFrame.js',
                     'dist_tmp/**/wallteredenv.js',
                     'dist_tmp/**/jquery-html5Validate.js',
                     'dist_tmp/**/comprice_ui.js',
                     'dist_tmp/**/insurance_artificial.js',
                     'dist_tmp/**/insurance_risk.js',
                     'dist_tmp/**/premium_artificial.js',
                     'dist_tmp/**/permium_ui.js'])
        .pipe(rev())
        .pipe(gulp.dest('dist/'))
        .pipe( rev.manifest() )
        .pipe( gulp.dest( 'dist/rev/js' ) );
});

gulp.task('rev',function(){
	return gulp.src(['dist/rev/**/*.json','src/main/webapp/WEB-INF/views/**/*.jsp'])
		.pipe(revCollector({
			replaceReved: true
		}))
		.pipe(gulp.dest('dist/WEB-INF/views/'));
});
gulp.task('minify', function() {
	  return gulp.src('dist/WEB-INF/views/**/*.jsp')
	    .pipe(htmlclean())
	    .pipe(gulp.dest('dist/WEB-INF/views/'));
	});
//gulp.task('default',['clean'],function(){
//	gulp.start('css','scripts','rev');
//{
   // protect: /<\!--%fooTemplate\b.*?%-->/g,
   // edit: function(html) { return html.replace(/\begg(s?)\b/ig, 'omelet$1'); }
  //}

//});

gulp.task('default',function(){
	runSequence('clean','clean_tmp','css','scripts','rev','minify');
});