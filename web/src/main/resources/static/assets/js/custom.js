'use strict';
//  Author: AdminDesigns.com
// 
//  This file is reserved for changes made by the use. 
//  Always seperate your work from the theme. It makes
//  modifications, and future theme updates much easier 
// 

(function ($) {
    // Place custom scripts here

    // Variables
    var Window = $(window);
    var Body = $('body');
    var Navbar = $('.navbar');
    var Topbar = $('#topbar');
    var CenterTray = $('.tray-center');
    var Footer = $('#content-footer');

    var Filters = $('#sidebar-right-filters');
    var AddMonitor = $('#sidebar-right-add-monitor');

    var options = {
        sbl: "sb-l-o", // sidebar left open onload
        sbr: "sb-r-o", // sidebar right closed onload
        sbState: "save", //Enable localstorage for sidebar states

        collapse: "sb-l-m", // sidebar left collapse style
        siblingRope: false, // Setting this true will reopen the left sidebar, when the right sidebar is closed
        trayHeight: Window.height() - (Navbar.outerHeight() + Topbar.outerHeight() + (CenterTray.outerHeight() - CenterTray.height()) + Footer.outerHeight())
    };

    // Init Theme Core
    Core.init(options);

    //Override sidemenu toggles
    $("#toggle_sidemenu_l").unbind('click').on('click', function () {
        // We check to see if the the user has closed the entire
        // leftside menu. If true we reopen it, this will result
        // in the menu resetting itself back to a minified state.
        // A second click will fully expand the menu.
        if (Body.hasClass('sb-l-c') && options.collapse === "sb-l-m") {
            Body.removeClass('sb-l-c');
        }

        // Toggle sidebar state(open/close)
        // Body.toggleClass(options.collapse).removeClass('sb-r-o').addClass('sb-r-c');
        Body.toggleClass(options.collapse);
        triggerResize();
    });

    $("#toggle_sidemenu_r").unbind('click');

    $(".sidebar-right-filters-toggle").on('click', function () {
        toggleSidebarSection(Filters);
    });

    $(".sidebar-right-add-monitor-toggle").on('click', function () {
        toggleSidebarSection(AddMonitor);
    });

    function toggleSidebarSection(section) {
        if(Body.hasClass('sb-r-o') && section.hasClass('hidden')) {
            Body.toggleClass('sb-r-o sb-r-c').delay(200).queue(function(next) {
                showSidebarSection(section);
                next();
            });
        } else {
            showSidebarSection(section);
        }
    }

    function showSidebarSection(section) {
        Body.toggleClass('sb-r-o sb-r-c');
        $('.sidebar-right-section').addClass('hidden');
        section.toggleClass('hidden');
        triggerResize();
    }

    // Init jQuery Multi-Select
    if ($("#topbar-multiple").length) {
        $('#topbar-multiple').multiselect({
            buttonClass: 'btn btn-default btn-sm ph15',
            dropRight: true
        });
    }

    // Most CSS menu animations are set to 300ms. After this time
    // we trigger a single global window resize to help catch any 3rd
    // party plugins which need the event to resize their given elements
    var triggerResize = function () {
        setTimeout(function () {
            $(window).trigger('resize');

            if (Body.hasClass('sb-l-m')) {
                Body.addClass('sb-l-disable-animation');
            }
            else {
                Body.removeClass('sb-l-disable-animation');
            }
        }, 300)
    };
    var highColors = [bgSystem, bgSuccess, bgWarning, bgPrimary];

    // Chart data
    var seriesData = [{
        name: 'Phones',
        data: [5.0, 9, 17, 22, 19, 11.5, 5.2, 9.5, 11.3, 15.3, 19.9, 24.6]
    }, {
        name: 'Notebooks',
        data: [2.9, 3.2, 4.7, 5.5, 8.9, 12.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
    }, {
        name: 'Desktops',
        data: [15, 19, 22.7, 29.3, 22.0, 17.0, 23.8, 19.1, 22.1, 14.1, 11.6, 7.5]
    }, {
        name: 'Music Players',
        data: [11, 6, 5, 15, 17.0, 22.0, 30.8, 24.1, 14.1, 11.1, 9.6, 6.5]
    }];

    var ecomChart = $('#ecommerce_chart1');

    if (ecomChart.length) {
        ecomChart.highcharts({
            credits: false,
            colors: highColors,
            chart: {
                backgroundColor: 'transparent',
                className: 'br-r',
                type: 'line',
                zoomType: 'x',
                panning: true,
                panKey: 'shift',
                marginTop: 45,
                marginRight: 1,
            },
            title: {
                text: null
            },
            xAxis: {
                gridLineColor: '#EEE',
                lineColor: '#EEE',
                tickColor: '#EEE',
                categories: ['Jan', 'Feb', 'Mar', 'Apr',
                    'May', 'Jun', 'Jul', 'Aug',
                    'Sep', 'Oct', 'Nov', 'Dec'
                ]
            },
            yAxis: {
                min: 0,
                tickInterval: 5,
                gridLineColor: '#EEE',
                title: {
                    text: null,
                }
            },
            plotOptions: {
                spline: {
                    lineWidth: 3,
                },
                area: {
                    fillOpacity: 0.2
                }
            },
            legend: {
                enabled: true,
                floating: false,
                align: 'right',
                verticalAlign: 'top',
            },
            series: seriesData
        });
    }

})(jQuery);