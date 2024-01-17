package services

import models.Error

class PDNSClientException(error: Error) : Throwable(error.error)