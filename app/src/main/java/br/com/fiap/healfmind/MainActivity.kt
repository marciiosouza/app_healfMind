package br.com.fiap.healfmind

import LoginScreen
import LoginScreenViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.fiap.healfmind.model.Usuarios
import br.com.fiap.healfmind.screens.CadastroScreen
import br.com.fiap.healfmind.screens.HomeScreen
import br.com.fiap.healfmind.screens.MarcarConsultaScreen
import br.com.fiap.healfmind.screens.MeditacoesScreen
import br.com.fiap.healfmind.screens.PerfilScreen
import br.com.fiap.healfmind.screens.VideoPlayerScreen
import br.com.fiap.healfmind.ui.theme.HealfMindTheme
import br.com.fiap.healfmind.viewModel.CadastroScreenViewModel
import br.com.fiap.healfmind.viewModel.MarcarConsultaScreenViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.compose.material3.Surface as Surface



class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberAnimatedNavController()

            //MainScreen()

            HealfMindTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background,

                    )
                {
                    var usuario by remember { mutableStateOf(Usuarios(0, "", "", "", "")) }

                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "Login",
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentScope.SlideDirection.End,
                                tween(800)
                            ) + fadeOut(animationSpec = tween(1000))
                        },
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentScope.SlideDirection.Down,
                                tween(1000)
                            )
                        }
                    ) {

                        composable(route = "Login") {
                            //var usuario by remember { mutableStateOf(Usuarios(0, "", "", "", "")) }
                            LoginScreen(LoginScreenViewModel(), navController, usuario) { it ->

                                usuario = it
                                Log.i("MainAct", "onCreate:${usuario} ")
                            }
                        }
                        composable(route = "Home") {
                            // var nome = it.arguments?.getString("nome")
                            HomeScreen(navController, usuario) // double bang -> Tratar valoresNull
                            //HomeScreen( ) // double bang -> Tratar valoresNull
                        }
                        composable(route = "Perfil") {

                            PerfilScreen(navController, usuario)
                        }
                        composable(route = "Cadastro") {
                            CadastroScreen(navController, CadastroScreenViewModel())
                        }
                        composable(route = "MarcarConsulta") {
                            MarcarConsultaScreen(MarcarConsultaScreenViewModel())
                        }
                        composable(route = "Meditacoes") {
                            MeditacoesScreen(navController, usuario)
                        }
                        composable(route = "VideoMeditacao") {
                            VideoPlayerScreen()
                        }
                    }
                }
            }
        }
    }
}

