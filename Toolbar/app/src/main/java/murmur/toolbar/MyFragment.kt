package murmur.toolbar

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu

class MyFragment : Fragment() {
    companion object {
        fun getInstance(): MyFragment = MyFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.fragment, container, false)
        setHasOptionsMenu(true)
        return view!!
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.frag_enter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.enter_menu -> {
                showPopUpMenu()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    /*
    PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_tasks, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.active:
                    mPresenter.setFiltering(TasksFilterType.ACTIVE_TASKS);
                    break;
                case R.id.completed:
                    mPresenter.setFiltering(TasksFilterType.COMPLETED_TASKS);
                    break;
                default:
                    mPresenter.setFiltering(TasksFilterType.ALL_TASKS);
                    break;
            }
            mPresenter.loadTasks(false);
            return true;
        });

        popup.show();
     */

    private fun showPopUpMenu() {
        PopupMenu(activity, activity.findViewById(R.id.enter_menu)).apply {
            menuInflater.inflate(R.menu.frag_menu, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_in_frag_item1 -> {
                        Log.d("kanna", "touch frag pop item 1")
                    }
                    R.id.menu_in_frag_item2 -> {
                        Log.d("kanna", "touch frag pop item 2")
                    }
                }
                true
            }
            show()
        }
    }
}
