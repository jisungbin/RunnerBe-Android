package team.applemango.runnerbe.xml.superwheelpicker

fun interface OnScrollListener {
    fun onScrollStateChange(state: Int)

    companion object {
        const val SCROLL_STATE_IDLE = 0
        const val SCROLL_STATE_TOUCH_SCROLL = 1
        const val SCROLL_STATE_FLING = 2
    }
}
