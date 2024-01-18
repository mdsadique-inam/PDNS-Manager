package exceptions

import models.Error

class PDNSClientException(val error: Error) : Throwable(error.error)