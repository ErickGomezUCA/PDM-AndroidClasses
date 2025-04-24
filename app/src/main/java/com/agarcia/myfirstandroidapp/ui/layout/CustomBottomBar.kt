package com.agarcia.myfirstandroidapp.ui.layout

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CustomBottomBar(navItems: List<NavItem>,  selectedItem: String = "nowplaying", onItemSelected: (String) -> Unit) {
  NavigationBar(
    //containerColor = Color.Green,
    //contentColor = Color.Black,
  ) {

    navItems.forEach { item ->
      NavigationBarItem(
        label = { Text(item.label) },
        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
        selected = selectedItem == item.route,
        onClick = { onItemSelected(item.route) },
//        colors = NavigationBarItemDefaults.colors(
//          selectedIconColor = Color.Black,
//          unselectedIconColor = Color.Gray,
//          selectedTextColor = Color.Black,
//          unselectedTextColor = Color.Gray,
//          indicatorColor = Color.LightGray,
//        )
      )
    }
  }
}
