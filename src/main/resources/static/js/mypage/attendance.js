document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        height: 800,
        initialView: 'dayGridMonth',
        headerToolbar: {
            right: '',
            center: 'prev title next',
            left: ''
        },
        // eventContent: 'some text',

        eventContent: function (arg) {
            return {
                html: '<div>&ensp;출근 | ' + timeFormat(arg.event.extendedProps.startTime) +
                    '<br>&ensp;퇴근 | ' + timeFormat(arg.event.extendedProps.endTime) +
                    '</div>'
            };
        },

        events: function (fetchInfo, successCallback, failureCallback) {
            // var empId = getEmpId();
            var empId = 1;
            fetchDailyAttendance(empId).then(function (data) {
                var events = data.map(function (attendance) {
                    return {
                        title: '',
                        color: 'rgba(1,1,1,0)',
                        textColor: 'black',
                        start: attendance.workDate,
                        extendedProps: {
                            startTime: attendance.startTime,
                            endTime: attendance.endTime
                        }
                    };
                });
                successCallback(events);
            }).catch(function (error) {
                failureCallback(error);
            });
        },

    });
    calendar.render();
});

function fetchDailyAttendance() {
    var empId = 1
    return fetch('/attendance/daily/' + empId)
        .then(function (response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
}

function timeFormat(dateTimeString) {
    if (dateTimeString === null) {
        return '';
    }
    var date = new Date(dateTimeString);
    var hour = date.getHours();
    var minute = date.getMinutes();
    hour = hour >= 10 ? hour : '0' + hour;
    minute = minute >= 10 ? minute : '0' + minute;
    return hour + ':' + minute;
}