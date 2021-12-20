# -*- coding: utf-8 -*-
import json
import re
import requests
from bs4 import BeautifulSoup
from tqdm import tqdm


class CoronaVirusSpider(object):
    def __init__(self):

        self.home_url = 'https://ncov.dxy.cn/ncovh5/view/pneumonia'

    def get_content_from_url(self, url):
        """
        根据URL，获取响应内容字符串数据
        @param url:请求的URL
        @return:响应内容的字符串
        """
        response = requests.get(url)
        return response.content.decode()

    def parse_home_page(self, home_page, tag_id):
        """
        解析首页内容，获取解析后的Python类型的数据
        @param home_page: 首页的内容
        @param tag_id: Tag对象的id
        @return: 解析后Python数据
        """
        # 2. 从疫情首页, 提取疫情数据
        soup = BeautifulSoup(home_page, 'lxml')
        script = soup.find(id=tag_id)
        # script = soup.find(id="getListByCountryTypeService2true") 最近一日世界各国疫情数据
        # script = soup.find(id="getAreaStat") 最近一日全国各省疫情数据
        text = script.text

        # 3. 提取各国疫情数据的json字符串
        json_str = re.findall(r'\[.+\]', text)[0]

        # 4. 将json字符串转换为Python类型的数据
        data = json.loads(json_str)
        return data

    def parse_corona_virus(self, last_day_corona_virus, desc):
        """
        解析各(国)省疫情数据URL，获取每日疫情数据
        @param last_day_corona_virus: 最近一日各(国)省疫情数据
        @param desc:
        """
        # 定义一个列表，存储从1月22日以来各(国)省疫情数据
        corona_virus_list = []
        # 2. 遍历各(国)省疫情数据，获取统计的URL
        for area in tqdm(last_day_corona_virus, desc):
            # 3. 发送请求，获取各(国)省疫情的json数据
            statistics_data_url = area['statisticsData']
            statistics_data_json_str = self.get_content_from_url(statistics_data_url)
            # 4. 将json数据转换为Python类型的数据，并添加到列表里面
            statistics_data = json.loads(statistics_data_json_str)['data']
            # print(statistics_data)
            for one_day in statistics_data:
                one_day['provinceName'] = area['provinceName']
                if area.get('countryShortCode'):
                    one_day['countryShortCode'] = area['countryShortCode']
            # print(statistics_data)

            corona_virus_list.extend(statistics_data)
        return corona_virus_list

    def load(self, path):
        """
        根据路径，加载疫情数据
        @param path:
        @return:
        """
        with open(path, encoding='utf-8') as fp:
            data = json.load(fp)
        return data

    def save(self, data, path):
        """
        保存数据到指定的路径下
        @param data:
        @param path:
        """
        # 5. 以json文件对象，保存最近一日各国疫情数据
        with open(path, 'w', encoding='utf-8') as fp:
            json.dump(data, fp, ensure_ascii=False)

    def crawl_last_day_corona_virus_of_world(self):
        """
        采集最近一日的各国疫情数据
        """
        # 1. 发送请求，获取首页内容
        home_page = self.get_content_from_url(self.home_url)
        # 2. 解析首页内容，获取最近一日各国疫情数据
        last_day_corona_virus_of_world = self.parse_home_page(home_page, tag_id="getListByCountryTypeService2true")
        # 3. 保存数据
        self.save(last_day_corona_virus_of_world, './last_day_corona_virus_of_world.json')

    def crawl_corona_virus_of_world(self):
        """
        采集从2020年1月23日以来各国疫情数据
        """
        # 1. 加载各国疫情数据
        last_day_corona_virus_of_world = self.load('./last_day_corona_virus_of_world.json')
        # print(last_day_corona_virus_of_world)
        # 2. 解析各国疫情数据URL，获取每日疫情数据
        corona_virus_of_world = self.parse_corona_virus(last_day_corona_virus_of_world,
                                                        desc='采集1月23日以来各国疫情数据')
        # 3. 把列表以json格式保存为文件
        self.save(corona_virus_of_world, './corona_virus_of_world.json')

    def crawl_last_day_corona_virus_of_china(self):
        """
        采集最近一日全国各省疫情数据
        """
        # 1. 发送请求，获取疫情首页
        home_page = self.get_content_from_url(self.home_url)
        # 2. 解析疫情首页，获取最近一日各省疫情数据
        last_day_corona_virus_of_china = self.parse_home_page(home_page, tag_id="getAreaStat")
        # 3. 保存各省疫情数据
        self.save(last_day_corona_virus_of_china, './last_day_corona_virus_of_china.json')

    def crawl_corona_virus_of_china(self):
        """
        采集从2020年1月22日以来各省疫情数据
        """
        # 1. 加载各省疫情数据
        last_day_corona_virus_of_china = self.load('./last_day_corona_virus_of_china.json')
        #C:/log/data_log/last_day_corona_virus_of_china.json
        # print(last_day_corona_virus_of_china)
        # 2. 解析各省疫情数据URL，获取每日疫情数据
        corona_virus_of_china = self.parse_corona_virus(last_day_corona_virus_of_china,
                                                        desc='采集1月22日以来各省疫情数据')
        # 3. 把列表以json格式保存为文件
        self.save(corona_virus_of_china, './corona_virus_of_china.json')
    #     C:/log/data_log/corona_virus_of_china.json

    def run(self):
        self.crawl_last_day_corona_virus_of_world()
        self.crawl_corona_virus_of_world()
        self.crawl_last_day_corona_virus_of_china()
        self.crawl_corona_virus_of_china()

if __name__ == '__main__':
    spider = CoronaVirusSpider()
    spider.run()

