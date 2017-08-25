(function (module) {
  var eNotifyArea = document.getElementById('notification-area');

  function notifyUser(notification) {
    var notificationClass = 'notification notification--' + ( notification.type || 'info');
    var notificationDismissTime = notification.time || 10000;
    var eNotification = document.createElement('div');
    eNotification.setAttribute('class', notificationClass);

    function closeNotification() {
      if (eNotifyArea.contains(eNotification)) {
        eNotifyArea.removeChild(eNotification);
      }
    }

    var eNotificationMessage = document.createElement('span');
    eNotificationMessage.setAttribute('class', 'notification__message');
    eNotificationMessage.innerText = notification.message;
    var eNotificationCloseButton = document.createElement('span');
    eNotificationCloseButton.setAttribute('class', 'notification__close-button');
    eNotificationCloseButton.innerText = 'x';
    eNotificationCloseButton.addEventListener('click', closeNotification);

    eNotification.appendChild(eNotificationMessage);
    eNotification.appendChild(eNotificationCloseButton);

    eNotifyArea.prepend(eNotification);

    setTimeout(function () {
      closeNotification();
    }, notificationDismissTime);
  }

  if(module.notification) {
    notifyUser(module.notification, 'success');
  }

  // Export functions
  module.notifyUser = notifyUser;
})(window);